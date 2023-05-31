package BugiSquad.HaksikMatnam.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import java.util.HashMap;


// Jwt 가 유효한 토큰인지 인증하기 위한 filter
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    /** Jwt Provider 주입 **/
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /** filter 등록 **/
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HashMap<String, String> tokens = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        String accessToken = tokens.get("accessToken");

            if (accessToken != null) { // access token 확인
                if (jwtTokenProvider.validateToken(accessToken)) {
                    log.info("------ valid token -----");
                    log.info("accessToken : " + accessToken);
                    Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else {
                log.info("NOT EXIST ACCESS TOKEN");
            }
        filterChain.doFilter(request, response);
    }
}