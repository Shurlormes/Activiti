package com.voidforce.activiti.util;

import com.voidforce.activiti.bean.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionUtil {

    public static UserInfo currentUserDeatils() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user instanceof UserInfo) {
            return (UserInfo) user;
        }
        return null;
    }

}
