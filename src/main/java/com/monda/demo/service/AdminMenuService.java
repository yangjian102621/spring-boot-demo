package com.monda.demo.service;

import com.monda.demo.mapper.AdminMenuMapper;
import com.monda.demo.model.AdminMenu;
import com.monda.demo.vo.admin.AdminMenuVo;

import java.util.List;


/**
 * 后台菜单服务
 * @author yangjian
 * @since 2017-08-27
 */
public interface AdminMenuService extends BaseService<AdminMenuMapper, AdminMenu> {

    /**
     * 用户菜单存储到 session 的 key
     */
    String MENUS_SESSION_KEY = "USER_MENUS_GROUP";

    /**
     * 分组获取全部菜单
     * @return
     */
    List<AdminMenuVo> getAllMenusByGroup();

    /**
     * 分组获取可用的菜单
     * @return
     */
    List<AdminMenuVo> getEnabledMenusByGroup();
}
