package com.monda.demo.controller.admin;

import com.monda.demo.enums.ResultEnum;
import com.monda.demo.model.AdminMenu;
import com.monda.demo.service.AdminMenuService;
import com.monda.demo.vo.ResultVo;
import com.monda.demo.vo.admin.AdminMenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 后台管理菜单
 * @author yangjian
 * @since 2017/09/02.
 */
@Controller
@RequestMapping("admin/menu")
public class AdminMenuController extends BaseController<AdminMenuService, AdminMenu> {

    static Logger logger = LoggerFactory.getLogger(AdminMenuController.class);

    /**
     * 数据列表
     * @param modelMap
     * @param request
     * @return
     */
    @GetMapping(value = {"", "/", "index"})
    public String index(ModelMap modelMap, HttpServletRequest request) {

        List<AdminMenuVo> list = service.getAllMenusByGroup();
        modelMap.put("list", list);
        modelMap.put("title", "管理菜单列表");

        List<AdminMenu> menus = service.getItemsByCondition("pid=0");
        modelMap.put("menus", menus);
        return "admin/menu/menu_index";
    }

    @PostMapping(value = "/doAdd")
    @ResponseBody
    public ResultVo doAdd(AdminMenu menu, HttpServletRequest request) {

        menu.setAddtime(new Date());
        menu.setUpdatetime(new Date());
        menu.setEnable(1);
        if (null != service.add(menu)) {
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

        AdminMenu menu = service.getItemById(id);
        if(null == menu) {
            return ResultVo.instance(ResultEnum.NO_RECORDS);
        } else {
            ResultVo success = ResultVo.success();
            success.setItem(menu);
            return success;
        }
    }

    @PostMapping(value = "/doUpdate")
    @ResponseBody
    public ResultVo doUpdate(AdminMenu menu, HttpServletRequest request) {

        menu.setUpdatetime(new Date());
        if (service.update(menu)) {
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
        AdminMenu menu = new AdminMenu();
        menu.setId(id);
        menu.setEnable(getIntParam(request, "enable"));
        if (service.update(menu)) {
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
        //如果还有子菜单则不能删除
        List<AdminMenu> list = service.getItemsByCondition("pid=" + id);
        if(list.size() > 0) {
            return ResultVo.instance(ResultEnum.FAIL.getCode(), "请先删除子菜单");
        }
        return super.delete(id);
    }

}
