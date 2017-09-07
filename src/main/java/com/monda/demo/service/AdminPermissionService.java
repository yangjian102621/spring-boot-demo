package com.monda.demo.service;

import com.monda.demo.mapper.AdminPermissionMapper;
import com.monda.demo.model.AdminPermission;
import com.monda.demo.vo.admin.PermissionGroupVo;

import java.util.List;


/**
 * 管理员权限
 * @author yangjian
 * @since 2017-08-27
 */
public interface AdminPermissionService extends BaseService<AdminPermissionMapper, AdminPermission> {

	/**
	 * 获取权限分组列表
	 * @return
	 */
	List<PermissionGroupVo> getPermissionByGroup();
}
