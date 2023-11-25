package com.picsart.intership.config.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    @Value("${security.access_token.secret_key}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${security.refresh_token.secret_key}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Value("${security.access_token.live}")
    private int ACCESS_TOKEN_LIVE;

    @Value("${security.refresh_token.live}")
    private int REFRESH_TOKEN_LIVE;

    public String extractUsernameFromAccessToken(String accessToken) {
        return extractClaim(accessToken, Claims::getSubject, getAccessTokenSignInKey());
    }

    public String extractRefreshTokenUsername(String refreshToken) {
        return extractClaim(refreshToken, Claims::getSubject, getRefreshTokenSignInKey());
    }

    public String generateAccessToken(String username) {
        return generateToken(new HashMap<>(), username, ACCESS_TOKEN_LIVE, getAccessTokenSignInKey());
    }

    public String generateRefreshToken(String username) {
        return generateToken(new HashMap<>(), username, REFRESH_TOKEN_LIVE, getRefreshTokenSignInKey());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, Key key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    public <T> Optional<T> extractClaim(String token, String secretKey, Function<Claims, T> claimsResolver) {
        T info = null;
        try {
            Key key = getTokenSignInKey(secretKey);
            final Claims claims = extractAllClaims(token, key);
            info = claimsResolver.apply(claims);
        } catch (UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException | SignatureException |
                 MalformedJwtException e) {
            log.error(e.getMessage(), e.getClass());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getClass());
        }
        return Optional.ofNullable(info);
    }

    private String generateToken(Map<String, Object> extraClaims, String username, int expirationTime, Key key) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis() + expirationTime)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String context, long expirationTime, String secretKey) {
        return generateToken(new HashMap<>(), context, expirationTime, secretKey);
    }

    public String generateToken(Map<String, Object> claims, String context, long expirationTime, String secretKey) {
        Key key = getTokenSignInKey(secretKey);
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(context)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis() + expirationTime)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenExpired(String token) {
        DecodedJWT decode = JWT.decode(token);
        Date expiresAt = decode.getExpiresAt();
        return expiresAt.before(new Date());
    }

    public String getExpiredMessageFromToken(String token) {
        DecodedJWT decode = JWT.decode(token);
        Date expiresAt = decode.getExpiresAt();
        return "JWT expired at " + expiresAt + ". Current time " + new Date();
    }

    private Claims extractAllClaims(String token, Key key) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getAccessTokenSignInKey() {
        return getTokenSignInKey(ACCESS_TOKEN_SECRET_KEY);
    }

    private Key getRefreshTokenSignInKey() {
        return getTokenSignInKey(REFRESH_TOKEN_SECRET_KEY);
    }

    private Key getTokenSignInKey(String key) {
        byte[] decode = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(decode);
    }

}
