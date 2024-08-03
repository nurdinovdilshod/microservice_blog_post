package com.company.configuration.security;

import com.company.dtos.TokenResponse;
import com.company.enums.TokenTypes;
import com.ctc.wstx.shaded.msv_core.datatype.xsd.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static com.company.enums.TokenTypes.ACCESS;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.access.token.secret.key}")
    private String accessTokenSecretKey;
    @Value("${jwt.access.token.expiry}")
    private long expiryInMinutes;

    public TokenResponse generateToken(@NonNull String username) {
        TokenResponse tokenResponse = new TokenResponse();
        generateAccessToken(username, tokenResponse);
//        generateRefreshToken(username, tokenResponse);
        return tokenResponse;
    }
    public TokenResponse generateAccessToken(@NonNull String username, @NonNull TokenResponse tokenResponse) {
        tokenResponse.setAccessTokenExpiry(new Date(System.currentTimeMillis() + expiryInMinutes));
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(tokenResponse.getAccessTokenExpiry())
                .signWith(signKey(ACCESS), io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
        tokenResponse.setAccessToken(accessToken);
        return tokenResponse;
    }
    public boolean isValidToken(@NonNull String token, TokenTypes tokenType) {
        return getExpiry(token, tokenType)
                .after(new Date());
    }

    public String getUsername(@NonNull String token, TokenTypes tokenType) {
        return getClaims(token, tokenType).getSubject();
    }

    public Date getExpiry(String token, TokenTypes tokenType) {
        return getClaims(token, tokenType).getExpiration();
    }
    private Claims getClaims(String token, TokenTypes tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key signKey(TokenTypes tokenType) {
        byte[] bytes = Decoders.BASE64.decode(tokenType.equals(ACCESS) ? accessTokenSecretKey : null);
        return Keys.hmacShaKeyFor(bytes);
    }
}
