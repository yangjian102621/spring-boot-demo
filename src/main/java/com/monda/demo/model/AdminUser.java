package com.monda.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "admin_user")
public class AdminUser extends BaseEntity<Integer> implements Serializable {

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(); //json处理对象

    /**
     * 用户名，使用电话号码注册
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 角色ID集合 json
     */
    private String roleIds;

    /**
     * 密码
     */
    private String password;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否可用，1：可用，0：冻结
     */
    private Integer enable = 1;

    /**
     * 添加人
     */
    private Integer adder;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 当前用户对应的角色列表（对应关系是：many to many）
     */
    @Transient
    private List<AdminRole> roleList;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 获取用户名，使用电话号码注册
     *
     * @return username - 用户名，使用电话号码注册
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名，使用电话号码注册
     *
     * @param username 用户名，使用电话号码注册
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取添加时间
     *
     * @return addtime - 添加时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置添加时间
     *
     * @param addtime 添加时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * 获取更新时间
     *
     * @return updatetime - 更新时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置更新时间
     *
     * @param updatetime 更新时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 获取是否可用，1：可用，0：冻结
     *
     * @return enable - 是否可用，1：可用，0：冻结
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * 设置是否可用，1：可用，0：冻结
     *
     * @param enable 是否可用，1：可用，0：冻结
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 获取添加人
     *
     * @return adder - 添加人
     */
    public Integer getAdder() {
        return adder;
    }

    /**
     * 设置添加人
     *
     * @param adder 添加人
     */
    public void setAdder(Integer adder) {
        this.adder = adder;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleIds() {
        return roleIds;
    }

    /**
     * 以 List 格式返回角色ID
     * @return
     */
    public List<String> getRoleIdsList() {
        try {
            return OBJECT_MAPPER.readValue(this.getRoleIds(), List.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    /**
     * 设置角色ID
     * @param roleIds
     * @throws JsonProcessingException
     */
    public void setRoleIdsList(List<String> roleIds) throws JsonProcessingException {
        this.roleIds = OBJECT_MAPPER.writeValueAsString(roleIds);
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public List<AdminRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AdminRole> roleList) {
        this.roleList = roleList;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 密码盐，重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }

}