package sit.int222.nw1apisas.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sit.int222.nw1apisas.properties.JwtProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private JwtProperties jwtProperties;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails, String userEmail) {
        Map<String, Object> claims = new HashMap<>();
//        ดึงข้อมูลบทบาทจากอ็อบเจ็กต์ GrantedAuthority ที่เป็นส่วนหนึ่งของ userDetails ซึ่งเป็นอ็อบเจ็กต์ที่มีข้อมูลเกี่ยวกับ role ของผู้ใช้ ใช้ .iterator().next() คือเรียกข้อมูล role แรกที่พบในกรณีที่ผู้ใช้มีหลาย role และจะเลือก role แรกที่เจอเท่านั้น
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        claims.put("token_type", "ACCESS_TOKEN");
        claims.put("user_email", userEmail);
        System.out.println(jwtProperties.getAccessTokenExpired());
        return doGenerateToken(claims, userDetails.getUsername(), jwtProperties.getAccessTokenExpired());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "REFRESH_TOKEN");
        return doGenerateToken(claims, userDetails.getUsername(), jwtProperties.getRefreshTokenExpired());
    }

    public String generateOtpToken(String email, String otp, Integer categoryId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "OTP_TOKEN");
        claims.put("otp", otp);
        claims.put("categoryId", categoryId);
        return doGenerateToken(claims, email, jwtProperties.getOtpTokenExpired());
    }


    private String doGenerateToken(Map<String, Object> claims, String subject, long expirationInMilliSec) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMilliSec * 1000)) // Refresh token expiration in minutes
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    public String extractRefreshTokenFromHeaders(String refreshTokenWithHeader) {
        if (refreshTokenWithHeader != null && refreshTokenWithHeader.startsWith("Bearer ")) {
            return refreshTokenWithHeader.substring(7); // Remove "Bearer "
        }
        return null;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Claims getClaim(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

}
