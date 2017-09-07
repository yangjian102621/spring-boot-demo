package com.monda.demo.enums;

/**
 * 启用|禁用枚举
 *
 * @author pengkai <pengxiankaikai@163.com>
 * @since 2017/8/02
 */
public enum EnableTypeEnum {

    DISABLED(0, "冻结"),
    ENABLED(1, "可用");

    private Integer val;

    private String name;

    EnableTypeEnum(Integer val, String name) {
        this.val = val;
        this.name = name;
    }

    public Integer getVal() {
        return val;
    }

    public String getName() {
        return name;
    }
}
