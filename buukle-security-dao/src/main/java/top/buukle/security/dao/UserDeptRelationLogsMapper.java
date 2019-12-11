package top.buukle.security.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.buukle.security.entity.UserDeptRelationLogs;
import top.buukle.security.entity.UserDeptRelationLogsExample;

@Mapper
public interface UserDeptRelationLogsMapper {
    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    long countByExample(UserDeptRelationLogsExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int deleteByExample(UserDeptRelationLogsExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int insert(UserDeptRelationLogs record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int insertSelective(UserDeptRelationLogs record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    List<UserDeptRelationLogs> selectByExample(UserDeptRelationLogsExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    UserDeptRelationLogs selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int updateByExampleSelective(@Param("record") UserDeptRelationLogs record, @Param("example") UserDeptRelationLogsExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int updateByExample(@Param("record") UserDeptRelationLogs record, @Param("example") UserDeptRelationLogsExample example);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int updateByPrimaryKeySelective(UserDeptRelationLogs record);

    /**
     *
     * @mbg.generated Wed Dec 11 14:13:56 CST 2019
     */
    int updateByPrimaryKey(UserDeptRelationLogs record);
}