package com.zanclick.redpacket.user.filter;


import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.utils.JsonUtils;
import com.zanclick.redpacket.user.modal.UsernamePasswordToken;
import com.zanclick.redpacket.user.resolver.UserPermissionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拦截器
 *
 * @author duchong
 * @date 2020-5-27 11:26:24
 */
@Slf4j
public abstract class AbstractJwtAuthenticationFilter extends OncePerRequestFilter {

    protected static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,x-requested-with,Authorization,version,type");
        response.setHeader("Access-Control-Expose-Headers", "Authorization,version,type");
        if (request.getMethod().equalsIgnoreCase(RequestMethod.OPTIONS.name())) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            if (!isLoginUrl(request) && !isLogoutUrl(request) && !isProtectedUrl(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            if (isLoginUrl(request)) {
                this.login(request, response);
            }
            if (isLogoutUrl(request)) {
                this.logout(request);
            }
            if (isProtectedUrl(request)) {
                this.verify(request);
            }
            filterChain.doFilter(request, response);
        }catch (UserPermissionResolver.UsernameAndPasswordException e){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JsonUtils.toJson(Response.fail(e.getMessage())));
            return;
        }catch (UserPermissionResolver.AuthorizationException e){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JsonUtils.toJson(Response.expire(e.getMessage())));
            return;
        }catch (Exception e){
            log.error("请求操作异常:{}",request.getRequestURI(),e);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JsonUtils.toJson(Response.fail(e.getMessage())));
            return;
        }
    }

    /**
     * 获取登录用户名和登录密码
     *
     * @param request
     * @return UsernamePasswordToken
     */
    protected UsernamePasswordToken getLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return new UsernamePasswordToken(username, password);
    }

    /**
     * 权限相关
     *
     * @return
     */
    public abstract UserPermissionResolver getUserPermissionResolver();

    /**
     * 登录处理
     *
     * @param request
     * @param response
     */
    protected abstract void login(HttpServletRequest request, HttpServletResponse response) throws UserPermissionResolver.UsernameAndPasswordException;


    /**
     * 登出处理
     *
     * @param request
     */
    protected abstract void logout(HttpServletRequest request) throws UserPermissionResolver.UsernameAndPasswordException;

    /**
     * 登出处理
     *
     * @param request
     */
    protected abstract void verify(HttpServletRequest request) throws UserPermissionResolver.AuthorizationException;

    /**
     * 登录url
     *
     * @param request
     * @return true or false
     */
    protected abstract Boolean isLoginUrl(HttpServletRequest request);

    /**
     * 登出url
     *
     * @param request
     * @return true or false
     */
    protected abstract Boolean isLogoutUrl(HttpServletRequest request);

    /**
     * 是否为受保护的url
     *
     * @param request
     * @return true or false
     */
    protected abstract boolean isProtectedUrl(HttpServletRequest request);

}
