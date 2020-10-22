package com.tyf.baseproject.config.security;

import com.tyf.baseproject.dao.UserRepository;
import com.tyf.baseproject.entity.User;
import com.tyf.baseproject.service.MenuService;

import com.tyf.baseproject.ustil.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
* @Description: 登录成功后控制类
* @Author: Mr.Tan
* @Date: 2019/10/8 9:22
*/
@Component
public class SysAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final static Logger logger = LoggerFactory.getLogger(SysAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuService menuService;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = userRepository.findUserByUsername(SecurityUtil.getCurUserName());
        session.setMaxInactiveInterval(60 * 60 * 8);
        /**
         * 将当前用户的用户信息以及权限信息存入session中
         */
        session.setAttribute(SecurityUtil.CURRENT_USER_SESSION, user);
        session.setAttribute(SecurityUtil.CURRENT_USER_MENU, menuService.getMenusByUserId(user.getId()));
        Map<String,Object> buttonMap = new HashMap<>();
        buttonMap.put("clear","ROLE_TEST");
        session.setAttribute(SecurityUtil.CURRENT_USER_BUTTON,buttonMap);
        //将按钮权限存入
        redirectStrategy.sendRedirect(request,response,"/index");
        logger.info("用户-"+user.getUsername()+"-登录成功!");
    }


    @Override
    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
