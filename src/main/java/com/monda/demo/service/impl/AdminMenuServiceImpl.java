package com.monda.demo.service.impl;

import com.monda.demo.mapper.AdminMenuMapper;
import com.monda.demo.model.AdminMenu;
import com.monda.demo.service.AdminMenuService;
import com.monda.demo.vo.admin.AdminMenuVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 后台管理菜单
 * @author yangjian
 * @since 2017-09-02
 */
@Service
public class AdminMenuServiceImpl extends BaseServiceImpl<AdminMenuMapper, AdminMenu> implements AdminMenuService {

    @Override
    public List<AdminMenuVo> getAllMenusByGroup() {
        //获取一级菜单
        Example example = new Example(AdminMenu.class);
        example.setOrderByClause("sort asc");
        example.createCriteria().andCondition("pid=0");
        //获取二级菜单
        Example exampleSub = new Example(AdminMenu.class);
        exampleSub.setOrderByClause("sort asc");
        exampleSub.createCriteria().andCondition("pid>0");
        return getMenusByGroup(example, exampleSub);
    }

    @Override
    public List<AdminMenuVo> getEnabledMenusByGroup() {
        //获取一级菜单
        Example example = new Example(AdminMenu.class);
        example.setOrderByClause("sort asc");
        example.createCriteria().andCondition("pid=0");
        example.createCriteria().andCondition("enable=1");
        //获取二级菜单
        Example exampleSub = new Example(AdminMenu.class);
        exampleSub.setOrderByClause("sort asc");
        exampleSub.createCriteria().andCondition("pid>0");
        exampleSub.createCriteria().andCondition("enable=1");
        return getMenusByGroup(example, exampleSub);
    }

    /**
     * 获取两级菜单
     * @param example
     * @param exampleSub
     * @return
     */
    private List<AdminMenuVo> getMenusByGroup(Example example, Example exampleSub) {

        List<AdminMenu> items = mapper.selectByExample(example);
        //获取二级菜单
        List<AdminMenu> subitems = mapper.selectByExample(exampleSub);
        Map<Integer, List<AdminMenu>> subItemsMap = subitems.stream().collect(Collectors.groupingBy(AdminMenu::getPid));

        List<AdminMenuVo> menuVos = new ArrayList<>();
        for (AdminMenu menu : items) {
            AdminMenuVo menuVo = new AdminMenuVo();
            BeanUtils.copyProperties(menu, menuVo);
            menuVo.setSubMenus(subItemsMap.get(menu.getId()));
            //收集父菜单的权限集合{user:add, user:list}
            if (null != menuVo.getSubMenus()) {
                ArrayList<String> permissions = menuVo.getSubMenus().stream().map(AdminMenu::getPermission).collect(Collectors.toCollection(ArrayList::new));
                menuVo.setPermission(StringUtils.join(permissions, ","));
            }
            menuVos.add(menuVo);
        }
        return menuVos;
    }
}
