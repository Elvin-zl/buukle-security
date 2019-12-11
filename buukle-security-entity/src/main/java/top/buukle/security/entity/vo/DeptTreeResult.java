/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: RoleTreeResult
 * Author:   zhanglei1102
 * Date:     2019/8/13 17:52
 * Description: 角色树节点包装类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.entity.vo;

import top.buukle.security.entity.Dept;

/**
 * @description 〈角色树节点包装类〉
 * @author zhanglei1102
 * @create 2019/8/13
 * @since 1.0.0
 */
public class DeptTreeResult extends Dept {

    private boolean checked;
    private String name;
    private boolean nocheck;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return super.getDeptName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }
}