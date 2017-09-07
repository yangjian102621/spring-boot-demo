package com.monda.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 管理后台的切面程序
 * @author yangjian
 * @since 2017-08-23
 */
@Aspect
@Component
public class AdminAspect {

    private final static Logger logger = LoggerFactory.getLogger(AdminAspect.class);

    /**
     * 公共方法，记录日志,权限控制
     * 出去登录的控制器，其他控制器全部拦截
     */
//    @Around("execution(public * com.monda.demo.controller.admin.*..*(..)) " +
//            "&& !execution(public * com.monda.demo.controller.admin.LoginController.*(..))" +
//            "&& @annotation(permission)")
//    public void log(ProceedingJoinPoint joinPoint, RequiresPermissions permission){
//        String[] value = permission.value();
//        for (String s : value) {
//            logger.info("privileges =============>"+s);
//        }
//    }

    /**
     * 控制层执行之前执行doBefore
     */
//    @Before("log()")
//    public void doBefore(JoinPoint joinPoint){
////        logger.info("1111111111");
////
////        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////        HttpServletRequest request = attributes.getRequest();
////        // url
////        logger.info("url={}", request.getRequestURL());
////        logger.info("uri={}", request.getRequestURI());
////        // method
////        logger.info("method={}", request.getMethod());
////        // ip
////        logger.info("id={}", request.getRemoteAddr());
////        // 类方法
////        logger.info("class_Method={}", joinPoint.getSignature().getDeclaringTypeName()+','+joinPoint.getSignature()
////                .getName() );
////        // 参数
////        logger.info("args={}", joinPoint.getArgs());
//    }
//
//    /**
//     * 控制层执行结束后执行doAfter
//     */
////    @After("log()")
////    public void doAfter(){
////        //logger.info("2222222222");
////    }
//
//    /**
//     * 执行结束后返回的结果打印出来
//     * @param object
//     */
//    @AfterReturning(returning = "object", pointcut = "log()")
//    public void doAfterReturn(Object object){
////        logger.info("response={}", object.toString());
//    }
}
