package com.tyf.baseproject.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
* @Description: 权限控制器
* @Author: Mr.Tan
* @Date: 2019/10/8 9:21
*/
@Service
public class SysAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if(collection == null || collection.size() <= 0 ){
            return;
        }
        ConfigAttribute c;
        String roleName;
        for(Iterator<ConfigAttribute> iter = collection.iterator(); iter.hasNext();){
            c = iter.next();
            roleName = c.getAttribute();
            for(GrantedAuthority ga : authentication.getAuthorities()){
                if(roleName.trim().equals(ga.getAuthority())){
                    return;
                }
            }

        }
        throw new AccessDeniedException("没有权限");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
