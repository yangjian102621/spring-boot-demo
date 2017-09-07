package com.monda.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.monda.demo.enums.PermissionGroupEnum;
import com.monda.demo.mapper.AdminPermissionMapper;
import com.monda.demo.model.AdminPermission;
import com.monda.demo.service.AdminPermissionService;
import com.monda.demo.vo.admin.PermissionGroupVo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author yangjian
 * @since 2017-08-27
 */
@Service
public class AdminPermissonServiceImpl extends BaseServiceImpl<AdminPermissionMapper, AdminPermission> implements
		AdminPermissionService {

	public List<AdminPermission> getItemsByPage(Example example, int page, int pageSize) {

		PageHelper.startPage(page, pageSize);
		List<AdminPermission> adminPermissions = mapper.selectByExample(example);
		// 获取分组名称 groupName
		for (int i = 0; i < adminPermissions.size(); i++) {
			adminPermissions.get(i).setGroupName(PermissionGroupEnum.getName(adminPermissions.get(i).getGroup()));
		}
		return adminPermissions;
	}

	@Override
	public List<PermissionGroupVo> getPermissionByGroup() {
		// 获取所有权限
		List<AdminPermission> permissions = this.getAllItems();
		// 权限分组
		Map<String, List<AdminPermission>> listMap = permissions.stream().collect(Collectors.groupingBy(AdminPermission::getGroup));
		ArrayList<PermissionGroupVo> permissionGroupVos = new ArrayList<>();
		listMap.forEach((k,v)->{
			//创建 VO
			PermissionGroupVo vo = new PermissionGroupVo();
			vo.setGroup(k);
			vo.setGroupName(PermissionGroupEnum.getName(k));
			vo.setPermissionList(v);
			permissionGroupVos.add(vo);
		});
		return permissionGroupVos;
	}
}
