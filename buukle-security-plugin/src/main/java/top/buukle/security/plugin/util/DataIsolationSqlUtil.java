package top.buukle.security.plugin.util;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.CollectionUtils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.buukle.common.call.code.BaseReturnEnum;
import top.buukle.common.exception.CommonException;
import top.buukle.security.entity.User;
import top.buukle.security.entity.constants.DeptEnums;
import top.buukle.security.entity.vo.DeptSessionVo;
import top.buukle.security.plugin.annotation.DataIsolationAnnotation;
import top.buukle.util.StringUtil;
import top.buukle.common.log.BaseLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataIsolationSqlUtil {

	private final static BaseLogger LOGGER = BaseLogger.getLogger(DataIsolationSqlUtil.class);
	
	/** 无记录时默认in条件 */
	private static final String NO_CODE_LIST = "-95272795";

	/** in条件前缀 */
	private static final String CODE_IN_PREFIX = " in ( ";

	/** in条件后缀 */
	private static final String CODE_IN_SUFFIX = " )";
	
	/** 拼接片段常量 : where */
	private static final String SPLICING_WHERE = " where ";
	
	/** 拼接片段常量 : and */
	private static final String SPLICING_AND = " and ";
	
	/** 拼接片段常量 : group */
	private static final String SPLICING_GROUP = " group ";
	
	/** 拼接片段常量 : order */
	private static final String SPLICING_ORDER = " order ";

	/**
	 * 构建查询条件,调用解析方法
	 * @param sql
	 * @param tableName
	 * @param queryDimension
	 * @return
	 * @throws Exception 
	 */
	public static String handleSql( String sql, String tableName, String queryDimension, String dimensionFiledName) throws ExecutionException {
		// 获取当前用户下辖部门id列表
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		List<Integer> subDeptIdList = (List<Integer>) SessionUtil.get(request, SessionUtil.USER_DEPT_SUB_ID_LIST_KEY);
		// 获取当前用户下辖部门列表
		DeptSessionVo dept = SessionUtil.getUserDept(request);
		// 非登录状态请求
		if(null == subDeptIdList || dept == null){
			return sql;
		}
		// 获取当前用户
		User user = SessionUtil.getUser(request, response);
		// 校验参数
		validateParam(tableName,queryDimension,dimensionFiledName);
		// 处理 :回车,制表符,等
		sql = sql.replace(StringUtil.NEW_LINE, StringUtil.BLANK).replace(StringUtil.TABULACTOR, StringUtil.BLANK);
		LOGGER.info("解析sql,拼接开始:原始sql :{},表名 :{},查询唯度 :{},维度字段名 :{},",sql, tableName,queryDimension, StringUtil.isEmpty(dimensionFiledName) ? StringUtil.BLANK : dimensionFiledName);
		String codeInCondition = StringUtil.EMPTY;
		// 部门维度
		if(StringUtil.notEmpty(queryDimension) && queryDimension.equals(DataIsolationAnnotation.DIMENSION_DEPT)){
			if(dept.getLeader().equals(DeptEnums.leader.SELF_LEADER.level())){
				codeInCondition = new StringBuilder("creator_code").append(CODE_IN_PREFIX).append("'").append(user.getUserId()).append("'").append(CODE_IN_SUFFIX).toString();
			}else if(dept.getLeader().equals(DeptEnums.leader.LOCAL_LEADER.level())){
				codeInCondition = new StringBuilder(dimensionFiledName).append(CODE_IN_PREFIX).append(dept.getId()).append(CODE_IN_SUFFIX).toString();
			}else if(dept.getLeader().equals(DeptEnums.leader.PASS_LEADER.level())){
				codeInCondition = new StringBuilder(dimensionFiledName).append(CODE_IN_PREFIX).append(getIdStringSaperatedByComma(subDeptIdList)).append(CODE_IN_SUFFIX).toString();
			}else{
				throw new CommonException(BaseReturnEnum.FAILED,"数据权限异常!用户部门级别非法!");
			}
		}
		String finalSql = handleSql(sql, tableName, codeInCondition);
		LOGGER.info("解析sql,拼接结束 :finalSql:{}",finalSql);
		return finalSql;
	}
	
	/**
	 * 解析sql,调用拼接方法
	 * @param sql
	 * @param tableName
	 * @param codeInCondition
	 * @return
	 */
	public static String handleSql(String sql, String tableName, String codeInCondition) {
		Matcher matcher;
		if (sql.startsWith("select")) {
			matcher = Pattern.compile("select\\s.+from\\s" + tableName + "\\s(.*)where\\s(.*)").matcher(sql);
			if (matcher.find()) {
				return match(matcher.group(1).trim(),sql,codeInCondition);
			} else {
				matcher = Pattern.compile("select\\s.+from\\s(.*)" + tableName + "\\s(.*)where\\s(.*)").matcher(sql);
				if (matcher.find()) {
					if (StringUtil.isEmpty(matcher.group(2).trim())) {
						return splicingSql(sql, codeInCondition);
					} else {
						int index = matcher.group(2).trim().indexOf(StringUtil.BLANK);
						if (index > 0) {
							return splicingSql(sql, matcher.group(2).trim().substring(0, index) + StringUtil.DOT + codeInCondition);
						} else {
							return splicingSql(sql, matcher.group(2).trim() + StringUtil.DOT + codeInCondition);
						}

					}
				} else {
					matcher = Pattern.compile("select\\s.+from\\s" + tableName + "\\s(.*)").matcher(sql);
					if (matcher.find()) {
                        return match(matcher.group(1).trim(),sql,codeInCondition);
					} else {
						matcher = Pattern.compile("select\\s.+from\\s(.*)" + tableName + "\\s(.*)").matcher(sql);
						if (matcher.find()) {
                            return match(matcher.group(2).trim(),sql,codeInCondition);
						}

					}
				}
			}
		}
		return sql;
	}

    private static String match(String match, String sql, String codeInCondition) {
        if (StringUtil.isEmpty(match)) {
            return splicingSql(sql, codeInCondition);
        } else {
            if (match.indexOf(StringUtil.BLANK) > 0) {
                match = match.substring(0, match.indexOf(StringUtil.BLANK));
                return splicingSql(sql, match + StringUtil.DOT + codeInCondition);
            } else {
                return splicingSql(sql, match + StringUtil.DOT + codeInCondition);
            }
        }
    }

    /**
	 * 拼接
	 * @param sql
	 * @param tableNameAndCondition
	 * @return
	 */
	public static String splicingSql(String sql, String tableNameAndCondition) {
		int index = sql.indexOf(SPLICING_WHERE);
		//处理 where
		if (index > 0) {
			return sql.replace(SPLICING_WHERE, SPLICING_WHERE + tableNameAndCondition + SPLICING_AND);
		} else {
			//处理 group by
			index = sql.indexOf(SPLICING_GROUP);
			if (index > 0) {
				return sql.replace(SPLICING_GROUP, SPLICING_WHERE + tableNameAndCondition + SPLICING_GROUP);
			}
			//处理 order by
			index = sql.indexOf(SPLICING_ORDER);
			if (index > 0) {
				return sql.replace(SPLICING_ORDER, SPLICING_WHERE + tableNameAndCondition + SPLICING_ORDER);
			} 
			//处理 默认情况
			else {
				return sql + SPLICING_WHERE + tableNameAndCondition;
			}
		}
	}

	/**
	 * 获取所辖角色 字符串,逗号分隔
	 *
	 * @return
	 * @param subDeptIdList
	 */
	private static String getIdStringSaperatedByComma(List<Integer> subDeptIdList) {
		if (CollectionUtils.isEmpty(subDeptIdList)) {
		    return NO_CODE_LIST;
		}
		StringBuilder sb = new StringBuilder();
		for (Integer deptId : subDeptIdList) {
			sb.append(StringUtil.SINGLE_QUOTE).append(deptId).append(StringUtil.SINGLE_QUOTE).append(StringUtil.COMMA);
		}
		return sb.toString().substring(0,sb.toString().lastIndexOf(StringUtil.COMMA));
	}

	/**
	 * 参数校验
	 * @param tableName
	 * @param queryDimension
	 */
	private static void validateParam(String tableName, String queryDimension,String roleFiledName) {
	}

}