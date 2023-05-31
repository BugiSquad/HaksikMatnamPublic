package BugiSquad.HaksikMatnam.config.security;

import BugiSquad.HaksikMatnam.member.service.MemberDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    @Value("spring.jwt.secret") //  @Value application.yml 에 있는 값 가져오기
    private String secretKey;
    private final long tokenValidMillisecond = 1000L * 60 * 60 * 24 * 90; // 90일
    private final MemberDetailService memberDetailService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /** Jwt 토큰 생성 **/
    public String createToken(String userPk, String memberType) {

        Claims claims = Jwts.claims().setSubject("mailto:" + userPk);
//        claims.put("roles", memberType); //roles 은 user 엔티티에 있는 칼럼
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    /** Jwt 토큰으로 인증 정보를 조회 **/
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    /** Jwt 토큰에서 회원 구별 정보 추출 **/
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject().replace("mailto:", "");
    }

    /** Request의 Header에서 token 파싱 **/
    public HashMap<String, String> resolveToken(HttpServletRequest req) {

        HashMap<String, String> tokens = new HashMap<>();
        String tmp = req.getHeader("accessToken");
        tokens.put("accessToken", tmp);

        tmp = req.getHeader("refreshToken");
        tokens.put("refreshToken", tmp);

        return tokens;
    }

    /** Jwt 토큰의 유효성 + 만료일자 확인 **/
    public boolean validateToken(String jwtToken) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }

    }

    /** token 만료시키기 **/
    public void expireToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        claims.setExpiration(new Date());
    }
}