package com.monda.demo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.monda.demo.enums.ResultEnum;
import com.monda.demo.model.BaseEntity;
import com.monda.demo.service.BaseService;
import com.monda.demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * admin 模块通用控制器
 * @author yangjian
 * @since 2017/7/29.
 */
public abstract class BaseController<SERVICE extends BaseService, M extends BaseEntity> {

    @Autowired
    protected SERVICE service;

    /**
     * 查询条件
     */
    protected Example example;

    protected int pageSize = 12;

    /**
     * 获取数据列表
     * @param modelMap
     * @param request
     */
    protected void list(ModelMap modelMap, HttpServletRequest request) {

        if (null == getExample().getOrderByClause()) {
            getExample().setOrderByClause("id DESC");   //设置默认排序方式
        }
        int page = this.getIntParam(request, "page");
        List<M> list = service.getItemsByPage(getExample(), page, getPageSize());
        String pagination = this.getPagination(list, page, request);
        modelMap.put("list", list);
        modelMap.put("pageMenu", pagination);

    }

    /**
     * 打印分页
     * @param list
     * @param page
     * @param request
     * @return
     */
    protected String getPagination(List list, int page, HttpServletRequest request) {

        PageInfo pageInfo =  new PageInfo<>(list);
        if ( pageInfo.getTotal() > 0 ) {
            //获取当前页面的url,并移除page参数
            String queryString = removeQueryParam(request.getQueryString(), "page");
            String url = request.getRequestURI()+(queryString==null?"?page=":"?"+queryString+"&page=");
            //获取列表的页码
            int[] pageNums = pageInfo.getNavigatepageNums();

            StringBuilder stringBuilder = new StringBuilder("<div class=\"am-fr\">");
            stringBuilder.append("<ul class=\"am-pagination tpl-pagination\">");
            stringBuilder.append("<li class=\"am-disabled\"><a href=\"#\">总计:"+pageInfo.getTotal()+"</a></li>");
            if (pageInfo.isIsFirstPage()) {
                stringBuilder.append("<li class=\"am-disabled\"><a href=\"#\">«</a></li>");
            } else {
                stringBuilder.append("<li><a href=\""+url+pageInfo.getPrePage()+"\">«</a></li>");
            }

            for(int i = 0; i < pageNums.length; i++) {
                if (page == pageNums[i]) {
                    stringBuilder.append("<li class=\"am-active\"><a href=\"#\">"+page+"</a></li>");
                } else {
                    stringBuilder.append("<li><a href=\""+url+pageNums[i]+"\">"+pageNums[i]+"</a></li>");
                }
            }
            if (pageInfo.isIsLastPage()) {
                stringBuilder.append("<li class=\"am-disabled\"><a href=\"#\">»</a></li>");
            } else {
                stringBuilder.append("<li><a href=\""+url+pageInfo.getNextPage()+"\">»</a></li>");
            }

            return stringBuilder.toString();
        }
        return null;
    }

    /**
     * 删除某条记录
     * @return
     */
    public ResultVo delete(Object id) {
        if (service.deleteById(id)) {
            return ResultVo.instance(ResultEnum.SUCCESS.getCode(), "删除成功.");
        } else {
            return ResultVo.instance(ResultEnum.FAIL.getCode(), "删除失败.");
        }
    }

    /**
     * 从查询字符串中移除某个参数
     * @param queryString
     * @param key
     * @return
     */
    public static String removeQueryParam(String queryString, String key) {

        if ( null == queryString ) {
             return null;
        }
        if (queryString.indexOf("&") == -1) {
            return queryString;
        }
        String[] params = queryString.split("&");
        StringBuilder sb = new StringBuilder();
        for (String p : params) {
           if (p.startsWith(key+"=")) {
              continue;
           }
           if (sb.length()==0) {
               sb.append(p);
           } else {
               sb.append("&"+p);
           }
        }
        return sb.toString();
    }

    /**
     * 获取整形参数
     * @param request
     * @param name
     * @return
     */
    protected int getIntParam(HttpServletRequest request, String name) {
        try {
            return Integer.parseInt(request.getParameter(name));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取 boolean 参数
     * @param request
     * @param name
     * @return
     */
    protected boolean getBooleanParam(HttpServletRequest request, String name) {
        try {
            return Boolean.valueOf(request.getParameter(name));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取字符串参数
     * @param request
     * @param name
     * @return
     */
    protected String getStringParam(HttpServletRequest request, String name) {
        try {
            return request.getParameter(name).trim();
        } catch (Exception e) {
            return "";
        }
    }

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
