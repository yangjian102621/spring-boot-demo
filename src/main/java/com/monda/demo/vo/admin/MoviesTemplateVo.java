package com.monda.demo.vo.admin;

import java.util.Date;

public class MoviesTemplateVo{

    private String id;

    /**
     * 剧名
     */
    private String name;

    /**
     * 类型ID
     */
    private Integer type;

    /**
     * 演出人员
     */
    private String performers;

    /**
     * 演出时长（单位分钟），默认120分钟
     */
    private Integer duration;

    /**
     * 演出介绍
     */
    private String summary;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人
     */
    private String adder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPerformers() {
        return performers;
    }

    public void setPerformers(String performers) {
        this.performers = performers;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }
}