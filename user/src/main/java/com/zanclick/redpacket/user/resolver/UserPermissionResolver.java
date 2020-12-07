package com.zanclick.redpacket.user.resolver;

import com.zanclick.redpacket.user.modal.LoginUser;

/**
 * @author lvlu
 * @date 2019-03-06 16:13
 **/
public abstract class UserPermissionResolver {

    /**
     * 获取登录用户
     *
     * @param username
     * @return
     */
    public abstract LoginUser getLoginUser(String username);


    public abstract Boolean comparePassWord(String password, String intPutPwd, String salt);



    public static class UsernameAndPasswordException extends RuntimeException {
        public UsernameAndPasswordException() {
        }

        public UsernameAndPasswordException(String message) {
            super(message);
        }

        public UsernameAndPasswordException(Throwable cause) {
            super(cause);
        }

        public UsernameAndPasswordException(String message, Throwable cause) {
            super(message, cause);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    public static class AuthorizationException extends RuntimeException {
        public AuthorizationException() {
        }

        public AuthorizationException(String message) {
            super(message);
        }

        public AuthorizationException(Throwable cause) {
            super(cause);
        }

        public AuthorizationException(String message, Throwable cause) {
            super(message, cause);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

}
