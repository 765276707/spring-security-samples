package com.example.utils;

import com.alibaba.fastjson.JSON;
import com.example.dto.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Assert;
import org.springframework.lang.NonNull;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWT令牌工具类
 * @author xzb
 */
public class JwtUtils {

    // 令牌建造器
    private static JwtBuilder jwtBuilder;
    // 令牌解析器
    private static JwtParser jwtParser;

    private final static String ISSUER = "open-issuer";
    private final static String AUDIENCE = "open-audience";
    private final static Long EXPIRATION = 300000L;

    static {
        jwtBuilder = Jwts.builder();
        jwtParser = Jwts.parser();
    }

    /**
     * 生成令牌(RSA 私钥加密生成令牌)
     * @param username 自定义用户
     * @param privateKey RSA私钥
     * @return
     */
    public static String createToken(String username, PrivateKey privateKey) {
        // 构建
        jwtBuilder.setHeaderParam(Header.TYPE, "jwt")
                .setHeaderParam("alg", SignatureAlgorithm.RS256)
                .setId(generatorTokenId())
                .setIssuer(ISSUER)
                .setSubject(username)
                .setAudience(AUDIENCE)
                .setIssuedAt(getNow(0))
                .setExpiration(getNow(EXPIRATION))
                .signWith(SignatureAlgorithm.RS256, privateKey);
        return jwtBuilder.compact();
    }

    /**
     * 解析令牌获取用户名
     * @param strToken 字符串令牌
     * @param publicKey RSA公钥
     * @return
     */
    public static String getUsernameFromJwt(String strToken, PublicKey publicKey) {
        Claims claims = Jwts.parser().setSigningKey(publicKey)
                .parseClaimsJws(strToken)
                .getBody();
        return claims.getSubject();
    }

    /**
     * 随机生成token编号
     * @return
     */
    public static String generatorTokenId() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 获取当前时间
     * @param afterSecond  当前时间之后的几秒时间
     * @return
     */
    public static Date getNow(@NonNull long afterSecond) {
        Date now = new Date();
        now.setTime(now.getTime() + afterSecond);
        return now;
    }



//    public static void main(String[] args) {
//        JwtUser user = new JwtUser("xzb", "123456", null);
//        KeyPair keyPair = RsaUtils.getKeyPair();
//        String token = createToken(user, keyPair.getPrivate());
//        System.out.println("token => " + token);
//        String username = getUsernameFromJwt(token, keyPair.getPublic());
//        System.out.println(username);
//    }
}
