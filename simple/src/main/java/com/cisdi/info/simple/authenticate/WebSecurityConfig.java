package com.cisdi.info.simple.authenticate;

/**
 * Created by huangds on 2017/10/24.
 */

import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.util.Config;
import com.cisdi.info.simple.util.ModuleManager;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录配置
 */
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
    private static Logger logger = LogManager.getLogger();
    public final static String SESSION_KEY = "username";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

//        addInterceptor.excludePathPatterns("/error");
//        addInterceptor.excludePathPatterns("/login**");

        addInterceptor.addPathPatterns();
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Headers", "authorization, Origin, X-Requested-With, Content-Type, Accept");
            response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpStatus.SC_OK);
                response.getWriter().write("OPTIONS returns OK");
                return true;

            }
            HttpSession session = request.getSession();
            String uri = request.getRequestURI();
            LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
            if (uri.startsWith("/file/")) {
                return true;
            }
            if (Config.whiteURLs.contains(uri)) {
                return true;
            }
            if (loginUser != null) {
                boolean hasPermission = ModuleManager.hasPermission(uri, loginUser.getUserRoles());
                if (hasPermission) {
                    return true;
                }
            }
            logger.error(String.format("非法请求 %s,可能是恶意攻击，请报告管理员", uri));
            response.setStatus(401);
            return false;
        }
    }
}
