package example.config;

import example.domain.common.UserType;
import example.domain.common.UserVo;
import example.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 60 * 24;

    public String createToken(String email, Long id, UserType userType) {
        Claims claims = Jwts.claims()
                .setSubject(Aes256Util.encrypt(email))
                .setId(Aes256Util.encrypt(id.toString()));

        claims.put("roles", userType);

        Date now = new Date();
        Date ExpireDate = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(ExpireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 유효한 시간 내에 토큰인지 확인
     * @param jwtToken
     * @return
     */
    public boolean checkValidToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰으로 유저 정보 갖고오기
     * @param token
     * @return
     */
    public UserVo getUserVo(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Aes256Util.decrypt(claims.getId())),
                Aes256Util.decrypt(claims.getSubject()),
                claims.get("role", UserType.class)
                );
    }
}
