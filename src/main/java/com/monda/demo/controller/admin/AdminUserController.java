package com.monda.demo.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.monda.demo.enums.ResultEnum;
import com.monda.demo.model.AdminRole;
import com.monda.demo.model.AdminUser;
import com.monda.demo.service.AdminRoleService;
import com.monda.demo.service.AdminUserService;
import com.monda.demo.util.IPUtil;
import com.monda.demo.util.StringUtils;
import com.monda.demo.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 系统管理员
 * @author yangjian
 * @since 2017/7/26.
 */
@Controller
@RequestMapping("admin/manager")
public class AdminUserController extends BaseController<AdminUserService, AdminUser> {

    static Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private AdminRoleService roleService; //用户角色服务

    /**
     * 数据列表
     * @param modelMap
     * @param request
     * @return
     */
    @GetMapping(value = {"", "/", "index"})
    @RequiresPermissions("adminUser:list")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        Example example = new Example(AdminUser.class);
        //example.createCriteria().andCondition("id IN(190,191,192)");
        this.setExample(example);
        super.list(modelMap, request);
        modelMap.put("title", "管理员列表");
        return "admin/manager/manager_index";
    }

    /**
     * 添加数据
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/add")
    @RequiresPermissions("adminUser:add")
    public String add(ModelMap modelMap) {

        List<AdminRole> roles = roleService.getItemsByCondition("enable=1");
        modelMap.put("roles", roles);
        modelMap.put("title", "添加管理员");
        return "admin/manager/manager_add";
    }

    @PostMapping(value = "/doAdd")
    @ResponseBody
    public ResultVo doAdd(AdminUser adminUser, @RequestParam List<String> rids, HttpServletRequest request) throws
		    JsonProcessingException {

        adminUser.setAddtime(new Date());
        adminUser.setUpdatetime(new Date());
        adminUser.setLastLoginTime(new Date());
        adminUser.setLastLoginIp(IPUtil.getIp(request));
        adminUser.setSalt(StringUtils.getUUId());
        adminUser.setPassword(StringUtils.getPasswordHash(adminUser.getPassword(), adminUser.getCredentialsSalt(), 2));
        adminUser.setAdder((Integer) request.getAttribute("adminId"));
        adminUser.setEnable(1);

        //将 roleIds 转为 json 字符串
        adminUser.setRoleIdsList(rids);

        if (null != service.add(adminUser)) {
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
    @GetMapping(value = "/edit")
    @RequiresPermissions("adminUser:edit")
    public String edit(ModelMap modelMap, HttpServletRequest request) {

        Integer id = getIntParam(request, "id");
        AdminUser adminUser = service.getItemById(id);
        List<AdminRole> roles = roleService.getItemsByCondition("enable=1");
        List<String> roleIds = adminUser.getRoleIdsList();
        modelMap.put("roles", roles);
        modelMap.put("roleIds", roleIds);
        modelMap.put("item", adminUser);
        modelMap.put("title", "编辑管理员");
        return "admin/manager/manager_edit";
    }

    @PostMapping(value = "/doUpdate")
    @ResponseBody
    public ResultVo doUpdate(AdminUser adminUser, @RequestParam List<String> rids, HttpServletRequest request)
            throws
		    JsonProcessingException {

        Integer id = getIntParam(request, "id");
        adminUser.setRoleIdsList(rids);
        adminUser.setUpdatetime(new Date());
        adminUser.setId(id);
        if (null != adminUser.getPassword()) {
            AdminUser oldUser = service.getItemById(id);
            adminUser.setPassword(StringUtils.getPasswordHash(adminUser.getPassword(), oldUser.getCredentialsSalt(), 2));
        }
        if (service.update(adminUser)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }

    /**
     * 启用|禁用管理员
     * @param request
     * @return
     */
    @PostMapping(value = "/enable")
    @ResponseBody
    public ResultVo enable(HttpServletRequest request) {

        Integer id = getIntParam(request, "id");
        Integer enable = getIntParam(request, "enable");
        AdminUser adminUser = new AdminUser();
        adminUser.setId(id);
        adminUser.setEnable(enable);
        if (service.update(adminUser)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }


    /**
     * 检查用户名是否被注册
     * @param username
     * @return
     */
    @GetMapping(value = "/exists/{username}")
    @ResponseBody
    public ResultVo exists(@PathVariable("username") String username) {

        AdminUser condition = new AdminUser();
        condition.setUsername(username);
        AdminUser adminUser = service.getItem(condition);
        ResultVo<AdminUser> adminResultVo = new ResultVo<>();
        if (null != adminUser) {
            adminResultVo.setCode(ResultEnum.USERNAME_EXISTS.getCode());
            adminResultVo.setMessage(ResultEnum.USERNAME_EXISTS.getMessage());
        } else {
            adminResultVo.setCode(ResultEnum.SUCCESS.getCode());
        }
        return adminResultVo;
    }

    /**
     * 删除数据
     * @param request
     * @return
     */
    @GetMapping(value = "/delete")
    @ResponseBody
    public ResultVo delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        return super.delete(id);
    }

    /**
     * 批量删除数据
     * @param request
     * @return
     */
    @GetMapping(value = "/deletes")
    @ResponseBody
    public ResultVo deletes(HttpServletRequest request) {
        String idStr = request.getParameter("id_str");
        if (null == idStr) {
            return ResultVo.fail();
        }
        Example example = new Example(AdminUser.class);
        example.createCriteria().andCondition("id IN("+idStr+")");
        if (service.delete(example)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    @PostMapping(value = "/modifyPass")
    @ResponseBody
    public ResultVo modifyPass(HttpServletRequest request) {
        String oldPass = getStringParam(request, "oldpass");
        String newPass = getStringParam(request, "newpassword");
        Object adminId = request.getAttribute("adminId");
        AdminUser adminUser = service.getItemById(adminId);
        String password = StringUtils.getPasswordHash(oldPass, adminUser.getCredentialsSalt(), 2);
        if (!password.equals(adminUser.getPassword())) {
            return new ResultVo(ResultEnum.FAIL.getCode(), "原密码错误");
        }
        adminUser.setPassword(StringUtils.getPasswordHash(newPass, adminUser.getCredentialsSalt(), 2));
        if (service.update(adminUser)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }
}
