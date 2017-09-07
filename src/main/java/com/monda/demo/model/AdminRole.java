package com.monda.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.HashedMap;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Table(name = "admin_role")
public class AdminRole extends BaseEntity<Integer> implements Serializable {

    // json 处理工具
    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 角色名称
     */
    private String name;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否可用(0:不可用，1:可用)
     */
    private Integer enable;

    /**
     * 添加人
     */
    private Integer adder;

    /**
     * 角色权限，json字符串
     */
    private String permissions;


    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    /**
     * 获取权限map
     * @return
     */
    public Map<String, Object> getPermissionMap() {
        try {
            return objectMapper.readValue(this.permissions, Map.class);
        } catch (Exception e) {
            return new HashedMap();
        }
    }

    /**
     * 设置权限，传入权限数组["user::add", "user:edit"]
     * @param permissions
     * @return
     */
    public void setPermissionMap(String[] permissions) throws JsonProcessingException {
        //将字符串数组转为 map
        HashMap<String, Integer> map = new HashMap<>();
        for (String key : permissions) {
            map.put(key, 1);
        }
        this.permissions = objectMapper.writeValueAsString(map);
    }

    /**
     * 判断角色是否可用
     * @return
     */
    public boolean isEnabled() {
        return this.enable == 1;
    }
}