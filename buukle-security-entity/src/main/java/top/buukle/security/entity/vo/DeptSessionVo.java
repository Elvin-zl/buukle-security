/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: DeptSessionVo
 * Author:   zhanglei1102
 * Date:     2019/12/11 18:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.entity.vo;

import top.buukle.security.entity.Dept;

/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/12/11
 * @since 1.0.0
 */
public class DeptSessionVo extends Dept {

    private String leader;

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }
}