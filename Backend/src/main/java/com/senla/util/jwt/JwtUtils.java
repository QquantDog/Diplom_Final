package com.senla.util.jwt;

import com.senla.dto.user.auth.ClaimsUserDto;
import com.senla.model.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime.seconds}")
    private Long jwtLifetime;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateSimpleJwt(ClaimsUserDto claimsUser) {
        return Jwts.builder()
                .subject(claimsUser.getEmail())
                .claim("id", claimsUser.getUserId())
                .claim("role", claimsUser.getRole())
                .claim("name", claimsUser.getFirstName())
                .claim("surname", claimsUser.getLastName())
                .claim("phone_number", claimsUser.getPhoneNumber())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * jwtLifetime))
                .signWith(key())
                .compact();
    }


    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean isDataRelevant(Claims claims, User user) {
        if(!claims.getSubject().equals(user.getEmail())) return false;
        if(!claims.get("id", Long.class).equals(user.getId())) return false;
        return true;
    }

    public Claims parseJwtToken(String authToken) {
        try {
            return Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(authToken)
                    .getPayload();
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return null;
    }
}

