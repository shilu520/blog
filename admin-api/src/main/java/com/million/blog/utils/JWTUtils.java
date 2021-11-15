package com.million.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String jwtToken = "123456Mszlu!@#$$";

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);  //这里是B部分
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken,设置A部分
                .setClaims(claims) // body数据，要唯一，自行设置，将B部分放到这里
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact(); //生成token
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            //解析B部分
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
