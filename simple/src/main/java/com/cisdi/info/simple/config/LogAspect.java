package com.cisdi.info.simple.config;

import com.alibaba.fastjson.JSON;
import com.cisdi.info.simple.entity.log.Log;
import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.service.log.LogService;
import com.cisdi.info.simple.util.DateUtil;
import com.cisdi.info.simple.util.ModuleManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:chengbg
 * @date:2018/9/7
 */
@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private LogService logService;

    /**
     * 定义切面，拦截所有controller方法
     */
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && !execution(* com.cisdi.info.simple.controller.log.LogController.*(..))")
    public void controllerLog() {
    }


    /**
     * 前置处理，在调用controller方法后，用于日志记录
     *
     * @param joinPoint
     */
    @Before("controllerLog()")
    public void doBeforeController(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        String nowDateTime = DateUtil.getNowDateTime();
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String methodType = request.getMethod();
        Object[] args = joinPoint.getArgs();
        //序列化时过滤掉request,response,MultipartFile
        List<Object> logArgs = Arrays.stream(args)
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)&& !(arg instanceof MultipartFile)))
                .collect(Collectors.toList());
        if (loginUser != null && !Config.whiteURLs.contains(url)) {
            String userName = loginUser.getUserName();
            Operator operator = loginUser.getLoginOperator();
            String moduleCode = url.substring(1, url.lastIndexOf("/"));
            Module module = ModuleManager.findModuleByCode(moduleCode);
            if (module != null) {
                String moduleName = module.getName();
                String entity = moduleCode.substring(moduleCode.lastIndexOf("/") + 1);
                List<Permission> permissions = module.getPermissions();
                for (Permission permission : permissions) {
                    List<String> urls = permission.getUrls();
                    String operationContent = permission.getName();
                    for (String s : urls) {
                        if (s.equals(url)) {
                            String fullName = permission.getFullName();
                            int index = fullName.lastIndexOf(".");
                            String operationType = fullName.substring(index + 1);
                            //记录除查询以外的操作
                            if (!"查看".equals(operationType)) {
                                Log log = new Log();
                                log.setIpAddress(ip);
                                log.setUrl(url);
                                log.setLogDate(new Date());
                                log.setOperator(operator);
                                log.setOperationType(operationType);
                                log.setModule(moduleName);
                                log.setEntity(entity);
                                log.setOperationContent(userName + operationContent + "，内容：" + JSON.toJSON(logArgs));
                                log.setOperatorId(operator.getEId());
                                log.setLogType("用户日志");
                                logService.saveLog(log);
                            }
                        }
                    }
                }
            }
        }
        //打印日志
        logger.debug("-----------------------------------------------------------------------------------------------------");
        logger.debug("时间 = {}", nowDateTime);
        logger.debug("用户名 = {}", loginUser != null ? loginUser.getUserName() : "用户未登录");
        logger.debug("访问url = {}", url);
        logger.debug("请求方法类型 = {}", methodType);
        logger.debug("来源ip地址 = {}", ip);
        logger.debug("调用方法 = {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.debug("请求参数 = {}", JSON.toJSON(logArgs));
        logger.debug("-----------------------------------------------------------------------------------------------------");
    }

    @Around(value = "controllerLog()")
    public Object doAfterController(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long time = end - start;
        logger.debug("执行时间 = {}", time + "ms");
        return object;
    }

}
