package com.monda.demo.service;

import com.monda.demo.mapper.AdminUserMapper;
import com.monda.demo.model.AdminUser;


/**
 * 后台 admin 管理员服务
 * @author yangjian
 * @since 2017-07-25
 */
public interface AdminUserService extends BaseService<AdminUserMapper, AdminUser> {

    /**
     * 后台管理员登录 session key
     */
    String LOGIN_SESSION_KEY = "admin_user_session";

    /**
     * 管理员登录
     * @param username
     * @return
     */
    AdminUser login(String username);

    /**
     * 获取管理员以及他对应的角色
     * @param user
     * @return
     */
    AdminUser getUserAndRoles(AdminUser user);

}
