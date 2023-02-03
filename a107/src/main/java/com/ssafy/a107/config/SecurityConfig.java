package com.ssafy.a107.config;

import com.ssafy.a107.common.auth.CustomAccessDeniedHandler;
import com.ssafy.a107.common.auth.CustomAuthenticationEntryPoint;
import com.ssafy.a107.common.auth.CustomUserDetailService;
import com.ssafy.a107.common.auth.JwtAuthenticationFilter;
import com.ssafy.a107.common.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

//    /**
//     * 현재는 모든 요청을 허가하고 있지만 유저 기능이 완성된 뒤에는 유저 인증을 추가하여 사용해야함
//     * @param httpSecurity
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
////                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // jwt 인증 필터 완성후 활성화할것
//                .httpBasic().disable()
//                .csrf().disable()
//                .cors().configurationSource(configurationSource())
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .accessDeniedHandler(customAccessDeniedHandler)
//                .and()
//                .headers().frameOptions().disable().and()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll()
//                .anyRequest().permitAll().and()
//                .build();
//    }

//    /**
//     * 현재는 모든 요청을 허가하고 있지만 배포 환경에서는 바뀔 수 있음
//     * @return
//     */
//    @Bean
//    public CorsConfigurationSource configurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedOriginPattern("*");
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()

                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().hasRole("USER")

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)

                .and()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
