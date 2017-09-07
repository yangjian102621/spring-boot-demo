package com.monda.demo.service.impl;

import com.monda.demo.mapper.AdminUserMapper;
import com.monda.demo.model.AdminUser;
import com.monda.demo.service.AdminRoleService;
import com.monda.demo.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 后台 admin 管理员服务
 * @author yangjian
 * @since 2017-07-25
 */
@Service
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Autowired
    AdminRoleService roleService;

    @Override
    public AdminUser login(String username) {
        AdminUser bean = new AdminUser();
        bean.setUsername(username);
        return getUserAndRoles(bean);
    }

    @Override
    public AdminUser getUserAndRoles(AdminUser user) {
        AdminUser admin = this.getItem(user);
        //获取用户角色
        admin.setRoleList(roleService.getItemsByIds(admin.getRoleIdsList()));
        return admin;
    }


}
