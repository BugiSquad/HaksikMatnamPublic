package BugiSquad.HaksikMatnam.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true, // @Secured
        jsr250Enabled = true, // @RolesAllowed
        prePostEnabled = true // @PreAuthorize, @PostAuthorize
)
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests() //사용권한 체크
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Request Header에 accesstoken 상황 허용
                .requestMatchers(HttpMethod.POST, "/api/member/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/notice/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/menu/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/s3/**").permitAll()

                /** 홍석준 테스트 **/
//                .requestMatchers(HttpMethod.POST, "/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                .requestMatchers(HttpMethod.PATCH, "/**").permitAll()
//                .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 요청을 처리하기 전에 어떤 필터를 거치는지 설정함.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다

        return http.build();
    }
}