package com.extendablechattingbe.extendablechattingbe.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {
    private String secretKey = "ssu20170341";
    private long expire = 60 * 24 * 30;

    public String generateToken(String content){
        return Jwts.builder()
            .setId(content)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
            .signWith(SignatureAlgorithm.HS256,secretKey.getBytes(StandardCharsets.UTF_8))
            .compact();

    }

    public String getUserNameFromJwt(String jwt){
        return getClaims(jwt).getBody().getId();
    }

    public boolean validateToken(String jwt){
        return this.getClaims(jwt) != null;
    }

    public Jws<Claims> getClaims(String jwt){
        try {
            return Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwt);
        }catch (Exception e){
            log.error("jwt error");
            return null;
        }
    }
}
