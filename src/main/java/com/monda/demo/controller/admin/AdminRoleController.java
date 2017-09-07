package com.monda.demo.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monda.demo.enums.ResultEnum;
import com.monda.demo.model.AdminRole;
import com.monda.demo.service.AdminPermissionService;
import com.monda.demo.service.AdminRoleService;
import com.monda.demo.vo.ResultVo;
import com.monda.demo.vo.admin.PermissionGroupVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 后台管理员角色
 * @author yangjian
 * @since 2017/08/28.
 */
@Controller
@RequestMapping("admin/role")
public class AdminRoleController extends BaseController<AdminRoleService, AdminRole> {

    static Logger logger = LoggerFactory.getLogger(AdminRoleController.class);

    @Autowired
    private AdminPermissionService permissionService;

    /**
     * 数据列表
     * @param modelMap
     * @param request
     * @return
     */
    @GetMapping(value = {"", "/", "index"})
    public String index(ModelMap modelMap, HttpServletRequest request) {
        Example example = new Example(AdminRole.class);
        this.setExample(example);
        super.list(modelMap, request);
        modelMap.put("title", "管理员角色列表");
        return "admin/role/role_index";
    }

    @PostMapping(value = "/doAdd")
    @ResponseBody
    public ResultVo doAdd(AdminRole role, HttpServletRequest request) {

        role.setAddtime(new Date());
        role.setUpdatetime(new Date());
        role.setAdder((Integer) request.getAttribute("adminId"));
        role.setEnable(1);
        if (null != service.add(role)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }

    /**
     * 更新数据
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/edit/{id}")
    @ResponseBody
    public ResultVo edit(@PathVariable Integer id, ModelMap modelMap) {

        AdminRole role = service.getItemById(id);
        if(null == role) {
            return ResultVo.instance(ResultEnum.NO_RECORDS);
        } else {
            ResultVo success = ResultVo.success();
            success.setItem(role);
            return success;
        }
    }

    @PostMapping(value = "/doUpdate")
    @ResponseBody
    public ResultVo doUpdate(AdminRole role, HttpServletRequest request) {

        role.setUpdatetime(new Date());
        if (service.update(role)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }

    /**
     * 启用|禁用
     * @param request
     * @return
     */
    @PostMapping(value = "/enable")
    @ResponseBody
    public ResultVo enable(HttpServletRequest request) {

        Integer id = getIntParam(request, "id");
        Integer enable = getIntParam(request, "enable");
        AdminRole role = new AdminRole();
        role.setId(id);
        role.setEnable(enable);
        if (service.update(role)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }

    /**
     * 删除数据
     * @return
     */
    @GetMapping(value = "/delete/{id}")
    @ResponseBody
    public ResultVo delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    /**
     * 获取所有权限列表
     * @param roleId
     * @return
     */
    @GetMapping(value = "/permission/get/{roleId}")
    @ResponseBody
    public ResultVo getPermissions(@PathVariable int roleId) {
        ResultVo success = ResultVo.success();
        //查询所有权限列表
        List<PermissionGroupVo> permissions = permissionService.getPermissionByGroup();
        success.setItems(permissions);
        //查询已选中的权限
        AdminRole role = service.getItemById(roleId);
        success.setItem(role.getPermissionMap());
        return success;
    }

    /**
     * 更改用户权限
     * @param permissions
     * @param roleId
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/permission/save")
    @ResponseBody
    public ResultVo modifyPermissions(String[] permissions, Integer roleId) throws JsonProcessingException {

        AdminRole role = new AdminRole();
        role.setId(roleId);
        role.setPermissionMap(permissions);
        if (service.update(role)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }

}
