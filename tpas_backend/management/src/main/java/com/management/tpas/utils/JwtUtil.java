package com.management.tpas.utils;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.JacksonUtil;
import com.management.tpas.config.JwtConfig;
import com.management.tpas.model.UserMsgModel;
import io.jsonwebtoken.*;
import org.apache.tomcat.util.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname JwtUtil
 * @description token令牌工具, 参考https://blog.csdn.net/AdminGuan/article/details/100147488
 * @date 2020/8/9
 **/
public class JwtUtil {

    public static String createJWT(String subject, String keyStr) {
        // 使用默认签名算法HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT时间
        long nowTime = System.currentTimeMillis();
        Date nowDate = new Date(nowTime);
        // 生成签名使用的秘钥
        SecretKey key = generalKey(keyStr);
        JwtBuilder jwtBuilder = Jwts.builder()
                //.setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                //.setId(id)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(nowDate)           //iat: jwt的签发时间
                .setSubject(subject)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
        // 设置过期时间
        if (JwtConfig.EXPIRE_TIME >= 0) {
            long expTime = JwtConfig.EXPIRE_TIME * 60 * 60 * 1000; // expire_time * 小时
            jwtBuilder.setExpiration(new Date(nowTime + expTime));
        }
        //压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
        return jwtBuilder.compact();
    }

    /**
     * @param keyStr 源字符串
     * @return javax.crypto.SecretKey
     * @description 字串生成加密key
     * @author dude
     * @date 2020/8/9
     **/
    private static SecretKey generalKey(String keyStr) {
        byte[] encodedKey = Base64.decodeBase64(keyStr);
        // 使用AES加密算法根据整个数组构造秘钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * @param jwt 令牌
     * @return io.jsonwebtoken.Claims
     * @description 解密jwt
     * @author dude
     * @date 2020/8/9
     **/
    public static Claims parseJWT(String jwt) {
        // 生成签名的签名秘钥
        SecretKey key = generalKey(JwtConfig.JWT_SECRET);
        try {
            Jws<Claims> a = Jwts.parser() // defaultJwtParser
                    .setSigningKey(key) // 设置秘钥
                    .parseClaimsJws(jwt);

            return Jwts.parser() // defaultJwtParser
                    .setSigningKey(key) // 设置秘钥
                    .parseClaimsJws(jwt) // 设置解密对象
                    .getBody();
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.TOKEN_ERROR.code, ErrorCodeEnum.TOKEN_ERROR.msg);
        }
    }

    /**
     * @description 根据令牌解析用户信息
     * @param token 令牌
     * @return java.lang.String
     *
     * @author dude
     * @date 2020/9/3
     **/
    public static UserMsgModel getUserMsgByToken(String token) {
        String subject = parseJWT(token).getSubject();
        if(subject == null){
            return null;
        }
        return JacksonUtil.json2Object(subject, UserMsgModel.class);
    }
}
