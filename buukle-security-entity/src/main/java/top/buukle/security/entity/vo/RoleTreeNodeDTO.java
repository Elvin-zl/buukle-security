/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: RoleTreeNodeDTO
 * Author:   elvin
 * Date:     2019/12/10 20:59
 * Description: 角色树dto
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.entity.vo;

import top.buukle.security.entity.Role;

import java.util.List;

/**
 * @description 〈角色树dto〉
 * @author elvin
 * @create 2019/12/10
 * @since 1.0.0
 */
public class RoleTreeNodeDTO extends Role {

    private List<RoleTreeNodeDTO> subNode;

    private List<String> subUrl;

    public List<RoleTreeNodeDTO> getSubNode() {
        return subNode;
    }

    public void setSubNode(List<RoleTreeNodeDTO> subNode) {
        this.subNode = subNode;
    }

    public List<String> getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(List<String> subUrl) {
        this.subUrl = subUrl;
    }
}