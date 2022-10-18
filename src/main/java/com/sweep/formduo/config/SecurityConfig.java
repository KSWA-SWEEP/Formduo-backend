package com.sweep.formduo.config;

import com.sweep.formduo.util.exceptionhandler.JwtAccessDeniedHandler;
import com.sweep.formduo.util.exceptionhandler.JwtAuthenticationEntryPoint;
import com.sweep.formduo.jwt.CustomEmailPasswordAuthProvider;
import com.sweep.formduo.jwt.JwtFilter;
import com.sweep.formduo.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@EnableWebSecurity // 기본적인 웹보안을 사용하겠다는 것
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 사용을 위함
public class SecurityConfig { // WebSecurityConfigurerAdapter 를 확장하면 보안 관련된 설정을 커스터마이징 할 수 있음
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomEmailPasswordAuthProvider customEmailPasswordAuthProvider;


    /*
     * AuthenticationManager를 주입받기 위해서 빈으로 등록한다.
     * */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/api/v1/resp", "/api/v1/auth/**", "/swagger-ui/**", "/api-docs/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/surveys/**", "/api/v1/resp", "/api/v1/auth/**", "/api/v1/resp").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin().disable()
                .csrf().disable()
                .headers().disable()
                .httpBasic().disable()
                .rememberMe().disable()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(customEmailPasswordAuthProvider);

        return http.build();
    }
}


