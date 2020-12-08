package com.zanclick.redpacket.common.utils;

import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
public class LoginContext implements Serializable {

    private static final String CURRENTUSER = "Current-User";

    public static void setCurrentUser(RequestUser user) {
        if (user != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.setAttribute(CURRENTUSER, user);
        }
    }

    public static RequestUser getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return (RequestUser) request.getAttribute(CURRENTUSER);
    }

    @Data
    public static class RequestUser{

        private String id;

        private String username;

        private Integer type;

        private String uid;

        private String wayId;

        private String storeNo;

        private String salt;

        private String password;
    }
}
