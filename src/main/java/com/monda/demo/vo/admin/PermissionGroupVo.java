package com.monda.demo.vo.admin;

import com.monda.demo.model.AdminPermission;

import java.util.List;

/**
 * 权限分组Vo
 * @author yangjian
 */
public class PermissionGroupVo {

    /**
     * 分组 key
     */
    private String group;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 权限当前分组下面的权限列表
     */
    private List<AdminPermission> permissionList;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<AdminPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<AdminPermission> permissionList) {
        this.permissionList = permissionList;
    }
}