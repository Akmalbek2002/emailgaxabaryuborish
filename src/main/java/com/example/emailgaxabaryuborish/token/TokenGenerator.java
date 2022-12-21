package com.example.emailgaxabaryuborish.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenGenerator {
    String key="qwerty2541";

    public  String getToken(String username){

        long vaqt=60*60*100;
        Date yashashVaqti=new Date(System.currentTimeMillis()+vaqt);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(yashashVaqti)
                .signWith(SignatureAlgorithm.HS512,key)
                .compact();
        return token;
    }

    public String Malumot(String token){
        String username = Jwts
                .parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return  username;

    }
    public  boolean TokenTekshirish(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return  true;
        }
        catch (Exception e){
            return  false;
        }
    }
}
