package com.extendablechattingbe.extendablechattingbe.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
        }catch (SignatureException ex) {
            log.error("유효하지 않은 JWT 서명입니다.");
            throw ex;
        } catch (MalformedJwtException ex) {
            log.error("유효하지 않은 JWT 토큰 입니다.");
            throw ex;
        } catch (ExpiredJwtException ex) {
            log.error("만료된 JWT 토큰 입니다.");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            log.error("지원하지 않는 JWT 토큰 입니다.");
            throw ex;
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims 가 비어있습니다.");
            throw ex;
        }
    }
}
