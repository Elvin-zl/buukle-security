/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: UserForCrudVo
 * Author:   zhanglei1102
 * Date:     2019/12/11 23:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.entity.vo;

import top.buukle.security.entity.User;

/**
 * @description 〈〉
 * @author zhanglei1102
 * @create 2019/12/11
 * @since 1.0.0
 */
public class UserForCrudVo extends User {

    private Integer leader;

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }
}