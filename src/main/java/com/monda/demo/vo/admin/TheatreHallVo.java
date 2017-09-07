package com.monda.demo.vo.admin;

import java.util.Date;

public class TheatreHallVo {

    /**
     * id
     */
    private String id;

    /**
     * 剧场名称
     */
    private String theatreName;

    /**
     * 剧场ID
     */
    private String theatreId;

    /**
     * 厅名称
     */
    private String name;

    /**
     * 厅类型，B:大，M:中，S:小
     */
    private String type;

    /**
     * 大厅座位楼层数
     */
    private Integer layerNum;

    /**
     * 大厅座位数
     */
    private Integer seatsNum;

    /**
     * 排座ID
     */
    private String seatsId;

    /**
     * 是否可用，1：可用，0：冻结
     */
    private Integer enable;

    /**
     * 是否使用 （如果被正在上）

    private Boolean isUse;
     */

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLayerNum() {
        return layerNum;
    }

    public void setLayerNum(Integer layerNum) {
        this.layerNum = layerNum;
    }

    public Integer getSeatsNum() {
        return seatsNum;
    }

    public void setSeatsNum(Integer seatsNum) {
        this.seatsNum = seatsNum;
    }

    public String getSeatsId() {
        return seatsId;
    }

    public void setSeatsId(String seatsId) {
        this.seatsId = seatsId;
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

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
    }
}