package com.monda.demo.vo.admin;

import java.util.Date;

public class TheatreVo{

    private String id;

    private Integer page = 1;

    private Integer rows = 10;

    /**
     * 剧场名称
     */
    private String name;


    /**
     * 剧场联系电话
     */
    private String phone;

    /**
     * 省份id
     */
    private String proId;

    /**
     * 省份id
     */
    private String proName;

    /**
     * 城市id
     */
    private String cityId;

    /**
     * 城市id
     */
    private String cityName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 剧场介绍
     */
    private String company;

    /**
     * 厅数量
     */
    private Integer theatreAmount = 0;

    /**
     * 是否审核
     */
    private Integer ischeked;

    /**
     * 是否可用，1：可用，0：冻结
     */
    private Integer enable;

    /**
     * 添加时间
     */
    private Date addtime;

    /**
     * 添加人
     */
    private String adder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getTheatreAmount() {
        return theatreAmount;
    }

    public void setTheatreAmount(Integer theatreAmount) {
        this.theatreAmount = theatreAmount;
    }

    public Integer getIscheked() {
        return ischeked;
    }

    public void setIscheked(Integer ischeked) {
        this.ischeked = ischeked;
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

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}