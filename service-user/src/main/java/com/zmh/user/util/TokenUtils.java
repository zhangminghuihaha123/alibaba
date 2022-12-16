package com.zmh.user.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class TokenUtils {


    public static String getToken(String userID,String password){
        /*.withExpiresAt(DateUtil.offsetHour(new Date(),2))*/
        /*把USERID作为载荷保存在token中，2小时过期,最后以password作为密钥*/
       return JWT.create().withAudience(userID).withExpiresAt(DateUtil.offsetHour(new Date(),2)).sign(Algorithm.HMAC256(password));
    }


}
