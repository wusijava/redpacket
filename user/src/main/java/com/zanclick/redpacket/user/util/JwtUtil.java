package com.zanclick.redpacket.user.util;

import com.zanclick.redpacket.user.modal.LoginInfo;
import com.zanclick.redpacket.user.resolver.UserPermissionResolver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duchong
 * @date 2020-5-25 11:52:24
 **/
@Slf4j
public class JwtUtil {
    /**
     * JWT 加解密类型
     */
    private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
    /**
     * JWT 生成密钥使用的密码
     */
    private static String JWT_RULE = "DcPay666";

    /**
     * JWT 添加至HTTP HEAD中的前缀
     */
    private static final String JWT_SEPARATOR = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    /**
     * 使用指定密钥生成规则，生成JWT加解密密钥
     *
     * @param alg  加解密类型
     * @param rule 密钥生成规则
     * @return
     */
    public static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        // 将密钥生成键转换为字节数组
        byte[] bytes = Base64.decodeBase64(rule);
        // 根据指定的加密方式，生成密钥
        return new SecretKeySpec(bytes, alg.getJcaName());
    }


    public static final String USER_NAME = "username";

    public static final String USER_ID = "uid";

    public static final String TIME = "time";

    public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 生成 加密字符串
     *
     * @param uid
     * @param username
     * @return jwt
     */
    public static String generateToken(String uid, String username, String loginType, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>(8);
        map.put(USER_NAME, username);
        map.put(USER_ID, uid);
        map.put(TIME, yyyyMMddHHmmss.format(new Date()));
        String jwt = Jwts.builder()
                .setClaims(map)
                .signWith(JWT_ALG, generateKey(JWT_ALG, JWT_RULE))
                .compact();
        String token = JWT_SEPARATOR + jwt;
        LoginInfo info = new LoginInfo();
        info.setJwt(token);
        info.setType(loginType);
        info.setUid(uid);
        info.setUsername(username);
        String oldJwt = LoginUtils.setLoginCache(info);
        response.setHeader(HEADER_STRING, oldJwt);
        return jwt;
    }

    /**
     * 清除加密字符串
     *
     * @param request
     * @return jwt
     */
    public static void clearToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null) {
            throw new UserPermissionResolver.UsernameAndPasswordException("登录信息异常");
        }
        LoginUtils.clearLoginCache(token);
    }

    /**
     * 验证token是否正确
     *
     * @param request
     * @return subject
     */
    public static Map<String, Object> validateToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null) {
            log.error("登录TOKEN为空");
            throw new UserPermissionResolver.AuthorizationException("登录过期");
        }
        try {
            Map<String, Object> claims = null;
            claims = Jwts.parser()
                    .setSigningKey(generateKey(JWT_ALG, JWT_RULE))
                    .parseClaimsJws(token.replace(JWT_SEPARATOR, ""))
                    .getBody();
            if (claims != null) {
                String key = LoginUtils.getLoginCache(token);
                if (key == null) {
                    log.error("登录TOKEN已过期");
                    throw new UserPermissionResolver.AuthorizationException("登录过期");
                }
                return claims;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.error("登录TOKEN验证错误");
        throw new UserPermissionResolver.AuthorizationException("登录过期");
    }
}
