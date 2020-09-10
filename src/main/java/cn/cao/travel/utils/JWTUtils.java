package cn.cao.travel.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author cao
 * @create 2020/9/10 - 19:09
 */
public class JWTUtils {

    // 加密 秘钥
    private final static String SECRET = "!A#2y$^R%X*ZC%#";
    // 解码后的 JWT token 对象
    private static DecodedJWT decodedJWT;

    /**
     * 获取JWT token
     * @param map 保存到 token的值
     * @return JWT token
     */
    public static String getToken(Map<String,String> map){

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);//设置过期时间 7天

        JWTCreator.Builder builder = JWT.create();

        //map.forEach(builder::withClaim);
        map.forEach((k,v)->
            builder.withClaim(k,v)
        );
        String token = builder.withExpiresAt(instance.getTime())  //设置过期时间
                            .sign(Algorithm.HMAC256(SECRET));//Signature 签名

        return token;
    }

    /**
     * 校验 token，校验成功后，通过getDecodedJWT获取到解码后的 jwt token
     * @param token
     */
    public static void verifyToken(String token){
        decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * @return 解码后的 jwt token
     */
    public static DecodedJWT getDecodedJWT(){
        return decodedJWT;
    }
}
