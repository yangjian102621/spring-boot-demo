package com.monda.demo.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monda.demo.enums.ResultEnum;

import java.util.Collection;
import java.util.List;

/**
 * Ajax 请求返回统一 VO
 * @author yangjian
 * @since 2017/7/25.
 */
public class ResultVo<T> {

    /**
     * 错误代码,成功返回 000, 否则返回其他
     */
    private String code;

    /**
     * 返回提示信息
     */
    private String message;

    /**
     * 数据列表
     */
    private Collection<T> items;

    /**
     * 单条数据
     */
    private T item;

    /**
     * 附带信息
     */
    private Object extra;

    /**
     * 总记录数
     */
    private Integer count=0;

    /**
     * 页码
     */
    private Integer page = 0;

    /**
     * 每页记录数
     */
    private Integer pageSize = 0;

    public ResultVo() {
    }

    /**
     * 推荐使用这个构造方法,方便统一修改
     * @param resultEnum
     */
    public ResultVo(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public ResultVo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(String code, String message, List<T> items) {
        this.code = code;
        this.message = message;
        this.items = items;
    }

    public ResultVo(String code, String message, T item) {
        this.code = code;
        this.message = message;
        this.item = item;
    }

    public ResultVo(T item){
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = ResultEnum.SUCCESS.getMessage();
        this.item = item;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    /**
     * 返回一个成功的 VO
     * @return
     */
    public static ResultVo success() {
        return new ResultVo(ResultEnum.SUCCESS);
    }

    /**
     * 返回一个失败的 VO
     * @return
     */
    public static ResultVo fail() {
        return new ResultVo(ResultEnum.FAIL);
    }

    /**
     * 返回 ResultVo 实例
     * @return
     */
    public static ResultVo instance(String code, String message) {
        return new ResultVo(code, message);
    }

    /**
     * 返回 ResultVo 实例
     * @return
     */
    public static ResultVo instance(ResultEnum resultEnum) {
        return new ResultVo(resultEnum.getCode(), resultEnum.getMessage());
    }

    /**
     * 正确值判断
     * @return
     */
    public boolean isSuccess(){
        return ResultEnum.SUCCESS.getCode() == this.code;
    }

    /**
     *  输出 json 字符串
     * @return
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
