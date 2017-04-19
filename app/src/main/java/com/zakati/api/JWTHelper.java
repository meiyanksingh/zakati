package com.zakati.api;

import com.ta.utils.Lg;
import com.ta.utils.PrefMgr;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by vipin on 7/3/17.
 */

public class JWTHelper {


//    As already mentioned, all the defined JWT values are ultimately stored in a JSON Map. JWT standard names are provided as type-safe getters and setters for convenience. They are:
//    Issuer (iss): getIssuer() and setIssuer(String)
//    Subject (sub): getSubject() and setSubject(String)
//    Audience (aud): getAudience() and setAudience(String)
//    Expiration (exp): getExpiration() and setExpiration(Date)
//    Not Before (nbf): getNotBefore() and setNotBefore(Date)
//    Issued At (iat): getIssuedAt() and setIssuedAt(Date)
//    JWT ID (jti): getId() and setId(String)


    private static final long TOKEN_EXPIRE_TIME_IN_MILI = 10 * 60 * 1000;
    private static String secretKey = "98u3nj98-W(238sdk n09!Hnsad&jasKk";


    public static String getJWTToken() {

        long expTime = PrefMgr.get().getTokenExpiry();

        if (expTime == 0) return generateJwtToken();

        Date expDate = new Date(expTime);
        if (expDate.after(new Date())) {
            return PrefMgr.get().getJwt();
        } else {
            return generateJwtToken();
        }
    }

    public static String generateJwtToken() {
        Date currentDate = new Date();
        Date expDate = new Date(currentDate.getTime() + TOKEN_EXPIRE_TIME_IN_MILI);
        String token = "";
        try {
            token = Jwts.builder()
                    .claim("userId", PrefMgr.get().getUserId())
                    .setHeaderParam("typ", "JWE")
                    .setIssuer("ANDROID")
                    .setExpiration(expDate)
                    .setIssuedAt(currentDate)
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Lg.i("JWT_Token", token);

        PrefMgr.get().saveJwt(token);
        PrefMgr.get().setTokenExpiry(expDate.getTime());
        return token;
    }

}
