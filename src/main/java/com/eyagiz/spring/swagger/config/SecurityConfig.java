package com.eyagiz.spring.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = { "/swagger-ui.html"};

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz.anyRequest().permitAll())
                .securityMatchers(matcher -> matcher.requestMatchers(AUTH_WHITELIST))
                .httpBasic(Customizer.withDefaults())
                .headers(header -> header.contentSecurityPolicy(csp -> csp.policyDirectives( "script-src 'self' 'unsafe-inline' 'unsafe-eval'"))
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}
