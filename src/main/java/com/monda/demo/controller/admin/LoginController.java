package com.monda.demo.controller.admin;

import com.monda.demo.enums.ResultEnum;
import com.monda.demo.model.AdminUser;
import com.monda.demo.service.AdminMenuService;
import com.monda.demo.service.AdminUserService;
import com.monda.demo.util.IPUtil;
import com.monda.demo.vo.ResultVo;
import com.monda.demo.vo.admin.AdminMenuVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 系统管理员
 * @author yangjian
 * @since 2017/7/26.
 */
@Controller
@RequestMapping("admin/")
public class LoginController {

    static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    AdminMenuService adminMenuService;

    /**
     * 管理员登录
     * @param modelMap
     * @param request
     * @return
     */
    @GetMapping(value = "/login")
    public String login(ModelMap modelMap, HttpServletRequest request) {
        modelMap.put("title", "管理员登录");
        return "admin/manager/login";
    }

    @PostMapping(value = "/doLogin")
    @ResponseBody
    public ResultVo doLogin(HttpServletRequest request) {

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //token.setRememberMe(true);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        ResultVo resultVo = ResultVo.success();
        try {
            System.out.println("对用户[" + username + "]进行登录验证...验证开始");
            currentUser.login(token);
            System.out.println("对用户[" + username + "]进行登录验证...验证通过");
        } catch (AuthenticationException e) {
//            throw e.printStackTrace();;
            logger.error("验证失败");
            resultVo.setCode(ResultEnum.FAIL.getCode());
            resultVo.setMessage("验证失败, 用户名或者密码错误");
        }

        //验证是否登录成功
        if(currentUser.isAuthenticated()) {

            logger.info("用户[" + username + "]登录认证通过");
            //更新最后登录时间和最后登录 ip
            AdminUser user = (AdminUser) currentUser.getPrincipal();
            user.setLastLoginIp(IPUtil.getIp(request));
            user.setLastLoginTime(new Date());
            adminUserService.update(user);

            if (user.getEnable() == 0) {
                resultVo.setCode(ResultEnum.FAIL.getCode());
                resultVo.setMessage("您的账户被禁用了,请联系管理员.");
                return resultVo;
            }

            //登录成功
            request.getSession().setAttribute(AdminUserService.LOGIN_SESSION_KEY, user);
            //获取用户菜单
            List<AdminMenuVo> menuVos = adminMenuService.getEnabledMenusByGroup();
            request.getSession().setAttribute(AdminMenuService.MENUS_SESSION_KEY, menuVos);
            resultVo.setCode(ResultEnum.SUCCESS.getCode());
            resultVo.setMessage("登录成功.");
        }

        return resultVo;

    }

    @GetMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        request.getSession().setAttribute(AdminUserService.LOGIN_SESSION_KEY, null);
        response.sendRedirect("/admin/login");
    }

    @GetMapping(value = "/403")
    public String forbid(ModelMap modelMap) {
        modelMap.put("title", "您没有操作权限");
        return "admin/403";
    }

    @GetMapping(value = "/404")
    public String notFound(ModelMap modelMap) {
        modelMap.put("title", "页面没有找到");
        return "admin/404";
    }

}
