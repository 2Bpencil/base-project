package com.tyf.baseproject.ustil;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: tyf
 * @Date: 2019/7/8 17:25
 * @Description:
 */
public class SecurityUtil {

    final public static String CURRENT_USER_SESSION = "CurUser";
    final public static String CURRENT_USER_MENU = "curMenu";
    final public static String CURRENT_USER_BUTTON = "curButton";


    /**
    * @Description:  获取当前用户
    * @Param:
    * @return:
    * @Author: Mr.Tan
    * @Date: 2019/10/8 9:14
    */
    public static String getCurUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }



}
