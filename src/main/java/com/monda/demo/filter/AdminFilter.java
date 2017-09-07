package com.monda.demo.filter;

import com.monda.demo.model.AdminUser;
import com.monda.demo.service.AdminMenuService;
import com.monda.demo.service.AdminUserService;
import com.monda.demo.vo.admin.AdminMenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 后台模块过滤器, 在此实现登录, 注册session
 * @author yangjian
 * @since 2017/8/1.
 */
@WebFilter(filterName = "adminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {

    static final Logger logger = LoggerFactory.getLogger(AdminFilter.class);

    @Autowired
    AdminMenuService adminMenuService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AdminUser loginAdminUser = (AdminUser) request.getSession().getAttribute(AdminUserService.LOGIN_SESSION_KEY);
        List<AdminMenuVo> menuVos = (List<AdminMenuVo>) request.getSession().getAttribute(AdminMenuService.MENUS_SESSION_KEY);
        if ( null != loginAdminUser) {
            request.setAttribute("loginUser", loginAdminUser);
            request.setAttribute("adminId", loginAdminUser.getId());
            request.setAttribute("sysMenus", menuVos);
        }
        //logger.info(request.getRequestURI());
        if ( null == loginAdminUser
                && !request.getRequestURI().equals("/admin/login")
                && !request.getRequestURI().equals("/admin/doLogin")) {
            logger.info("请先登录");
            response.sendRedirect("/admin/login");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
