package com.tyf.baseproject.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description: 退出成功控制
* @Author: Mr.Tan
* @Date: 2019/10/8 9:23
*/
@Component
public class SysLogoutSuccessHandler implements LogoutSuccessHandler {

    private final static Logger logger = LoggerFactory.getLogger(SysLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        try {

            logger.info("用户-"+ authentication.getName()+"-退出系统!");
        } catch (Exception e) {
            logger.error("用户-"+ authentication.getName()+"-退出系统失败!" + e.getMessage());
        }
        httpServletResponse.sendRedirect("/login");
    }
}
