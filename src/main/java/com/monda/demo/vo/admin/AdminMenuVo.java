package com.monda.demo.vo.admin;

import com.monda.demo.model.AdminMenu;
import com.monda.demo.model.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AdminMenuVo extends BaseEntity<Integer> implements Serializable {

    /**
     * 父级菜单ID
     */
    private Integer pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 菜单权限
     */
    private String permission;

    /**
     * 排序数字,越大越靠后
     */
    private Integer sort;

    /**
     * 是否可用
     */
    private Integer enable;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 下级菜单列表
     */
    private List<AdminMenu> subMenus;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<AdminMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<AdminMenu> subMenus) {
        this.subMenus = subMenus;
    }
}