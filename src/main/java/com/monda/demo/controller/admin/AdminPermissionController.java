package com.monda.demo.controller.admin;

import com.monda.demo.enums.ResultEnum;
import com.monda.demo.model.AdminPermission;
import com.monda.demo.service.AdminPermissionService;
import com.monda.demo.vo.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 管理员角色权限
 * @author yangjian
 * @since 2017/08/28.
 */
@Controller
@RequestMapping("admin/permission")
public class AdminPermissionController extends BaseController<AdminPermissionService, AdminPermission> {

    static Logger logger = LoggerFactory.getLogger(AdminPermissionController.class);

    /**
     * 数据列表
     * @param modelMap
     * @param request
     * @return
     */
    @GetMapping(value = {"", "/", "index"})
    @RequiresPermissions("permission:list")
    public String index(ModelMap modelMap, HttpServletRequest request) {

        String name = getStringParam(request, "name");
        String group = getStringParam(request, "group");
        String key = getStringParam(request, "key");
        Example example = new Example(AdminPermission.class);
        //搜索
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(group)) {
            criteria.andCondition("pgroup='"+group+"'");
        }
        if (StringUtils.isNotEmpty(key)) {
            criteria.andLike("key", "%"+key+"%");
        }
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", "%"+name+"%");
        }
        example.setOrderByClause("pkey asc");
        this.setExample(example);
        super.list(modelMap, request);
        modelMap.put("title", "权限列表");
        return "admin/role/permission_index";
    }

    @PostMapping(value = "/doAdd")
    @ResponseBody
    @RequiresPermissions("permission:add")
    public ResultVo doAdd(AdminPermission permission, HttpServletRequest request) {

        permission.setAddtime(new Date());
        permission.setUpdatetime(new Date());
        if (null != service.add(permission)) {
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

        AdminPermission permission = service.getItemById(id);
        if(null == permission) {
            return ResultVo.instance(ResultEnum.NO_RECORDS);
        } else {
            ResultVo success = ResultVo.success();
            success.setItem(permission);
            return success;
        }
    }

    @PostMapping(value = "/doUpdate")
    @ResponseBody
    @RequiresPermissions("permission:edit")
    public ResultVo doUpdate(AdminPermission permission, HttpServletRequest request) {

        permission.setUpdatetime(new Date());
        if (service.update(permission)) {
            return ResultVo.success();
        } else {
            return ResultVo.fail();
        }
    }

    /**
     * 删除数据
     * @return
     */
    @RequiresPermissions("permission:delete")
    @GetMapping(value = "/delete/{id}")
    @ResponseBody
    public ResultVo delete(@PathVariable Integer id) {
        return super.delete(id);
    }

}
