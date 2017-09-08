package com.monda.demo.controller;

import com.monda.demo.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yangjian on 17-9-7.
 */
@Controller
public class IndexController {

	/**
	 * 首页
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = {"", "/"})
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("/admin/login");
	}

	@GetMapping(value = "/403")
	public void forbid(HttpServletResponse response) throws IOException {
		response.sendRedirect("/admin/403");
	}

	@GetMapping(value = "/404")
	public void notFound(HttpServletResponse response) throws IOException {
		response.sendRedirect("/admin/404");
	}

	@GetMapping(value = "/admin/index")
	public String adminIndex(HttpServletRequest request, ModelMap modelMap) {
		modelMap.put("title", "数据添加 Demo 页面");
		return "admin/index";
	}

	@GetMapping(value = "/admin/doAdd")
	public ResultVo adminDoAdd(HttpServletRequest request) {
		return ResultVo.success();
	}

}
