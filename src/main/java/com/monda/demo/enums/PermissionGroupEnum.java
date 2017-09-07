package com.monda.demo.enums;

/**
 * 权限分组枚举
 *
 * @author yangjian
 * @since 2017/8/02
 */
public enum PermissionGroupEnum {

    SYS("系统管理", "system"),
    USER("用户管理", "user"),
    MOVIE("影片管理", "movie"),
    THEATRE("剧场管理", "theatre");

    private String name;

    private String value;

    PermissionGroupEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据 name 获取 value
     * @param value
     * @return
     */
    public static String getName(String value) {
        for (PermissionGroupEnum groupEnum : PermissionGroupEnum.values()) {
            if(groupEnum.getValue().equals(value)) {
                return  groupEnum.getName();
            }
        }
        return null;
    }
}
