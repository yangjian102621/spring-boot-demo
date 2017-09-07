package com.monda.demo.handler;

import com.monda.demo.enums.ResultEnum;
import com.monda.demo.exception.AppException;
import com.monda.demo.vo.ResultVo;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Administrator on 2017/7/18.
 */
@ControllerAdvice
public class ExceptionHandler {

    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVo handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {

        //logger.error("======> {}", e);
        if (e instanceof AppException) {
            AppException exception = (AppException) e;
            return ResultVo.instance(exception.getCode(), exception.getMessage());
        }
        System.out.println(response.getStatus());
        if (e instanceof UnauthorizedException) {
            //ajax 请求
            if (request.getHeader("X-Requested-With") != null
                    && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString())) {
                return ResultVo.instance(ResultEnum.UN_AUTHORIZED);
            } else {
                response.sendRedirect("/admin/403");
                return null;
            }
        } else {
            return ResultVo.instance(ResultEnum.UNKONW_ERROR.getCode(), ResultEnum.UNKONW_ERROR.getMessage());
        }
    }
}
