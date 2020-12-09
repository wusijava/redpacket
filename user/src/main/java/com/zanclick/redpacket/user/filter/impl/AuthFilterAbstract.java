package com.zanclick.redpacket.user.filter.impl;

import com.zanclick.redpacket.common.sms.SendSms;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.user.filter.AbstractJwtAuthenticationFilter;
import com.zanclick.redpacket.user.modal.LoginInfo;
import com.zanclick.redpacket.user.modal.LoginUser;
import com.zanclick.redpacket.user.modal.UsernamePasswordToken;
import com.zanclick.redpacket.user.resolver.UserPermissionResolver;
import com.zanclick.redpacket.user.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author duchong
 * @description 具体的登录登出实现
 * @date 2020-5-27 14:02:44
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class AuthFilterAbstract extends AbstractJwtAuthenticationFilter {

    @Autowired
    @Qualifier(value = "webUserPermissionResolver")
    private UserPermissionResolver userPermissionResolver;

    @Override
    public UserPermissionResolver getUserPermissionResolver() {
        return userPermissionResolver;
    }

    @Override
    protected UsernamePasswordToken getLogin(HttpServletRequest request) {
        return super.getLogin(request);
    }

    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response) throws UserPermissionResolver.UsernameAndPasswordException {
        UsernamePasswordToken token = getLogin(request);
        if (DataUtils.isEmpty(token.getUsername())) {
            throw new UserPermissionResolver.UsernameAndPasswordException("请填写登录账号");
        }
        Boolean isSmsLogin = isSmsLogin(request);
        if (DataUtils.isEmpty(token.getPassword())){
            throw new UserPermissionResolver.UsernameAndPasswordException(isSmsLogin ? "请填写验证码" : "请填写登录账号");
        }
        LoginUser user = getUserPermissionResolver().getLoginUser(token.getUsername());
        if (isSmsLogin){
            Boolean smsVerify = SendSms.verifyLoginSms(token.getUsername(),token.getPassword());
            if (!smsVerify) {
                throw new UserPermissionResolver.UsernameAndPasswordException("验证码填写错误");
            }
        }else {
            Boolean pwdVerifyResult = getUserPermissionResolver().comparePassWord(user.getPassword(), token.getPassword(), user.getSalt());
            if (!pwdVerifyResult) {
                throw new UserPermissionResolver.UsernameAndPasswordException("账号密码错误");
            }
        }
        JwtUtil.generateToken(user.getUid(), user.getUsername(), getLoginType(request), response);
        setRequestUser(user);
    }

    @Override
    protected void logout(HttpServletRequest request) throws UserPermissionResolver.UsernameAndPasswordException {
        JwtUtil.clearToken(request);
    }

    @Override
    protected void verify(HttpServletRequest request) throws UserPermissionResolver.UsernameAndPasswordException {
        Map<String, Object> params = JwtUtil.validateToken(request);
        try {
            String username = (String) params.get(JwtUtil.USER_NAME);
            LoginUser user = getUserPermissionResolver().getLoginUser(username);
            setRequestUser(user);
        } catch (UserPermissionResolver.UsernameAndPasswordException e) {
            throw new UserPermissionResolver.AuthorizationException(e.getMessage());
        } catch (Exception e) {
            log.error("登录信息异常:", e);
            throw new UserPermissionResolver.AuthorizationException("登录过期");
        }
    }

    @Override
    protected Boolean isLoginUrl(HttpServletRequest request) {
        return pathMatcher.match("/h5/login", request.getServletPath()) || pathMatcher.match("/h5/sms/login", request.getServletPath()) || pathMatcher.match("/web/user/login", request.getServletPath());
    }

    @Override
    protected Boolean isLogoutUrl(HttpServletRequest request) {
        return pathMatcher.match("/**/logout", request.getServletPath());
    }

    @Override
    protected boolean isProtectedUrl(HttpServletRequest request) {
        return pathMatcher.match("/api/**", request.getServletPath())
                || pathMatcher.match("/**/api/**",request.getServletPath());
    }

    protected String getLoginType(HttpServletRequest request) {
        if (pathMatcher.match("/web/**", request.getServletPath())) {
            return LoginInfo.Type.WEB.getCode();
        } else if (pathMatcher.match("/h5/**", request.getServletPath())) {
            return LoginInfo.Type.H5.getCode();
        }
        return LoginInfo.Type.OPEN.getCode();
    }

    protected Boolean isSmsLogin(HttpServletRequest request) {
        return pathMatcher.match("/h5/sms/login", request.getServletPath());
    }

    protected void setRequestUser(LoginUser user) {
        LoginContext.RequestUser requestUser = new LoginContext.RequestUser();
        requestUser.setId(user.getId());
        requestUser.setUid(user.getUid());
        requestUser.setUsername(user.getUsername());
        requestUser.setSalt(user.getSalt());
        requestUser.setPassword(user.getPassword());
        //requestUser.setNickname(user.getNickname());
        //requestUser.setPhone(user.getPhone());
        LoginContext.setCurrentUser(requestUser);
    }
}
