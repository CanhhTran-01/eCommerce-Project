package com.myproject.ecommerce.security;

import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.security.handler.JwtAccessDeniedHandler;
import com.myproject.ecommerce.security.handler.JwtAuthenticationEntryPoint;
import com.myproject.ecommerce.security.handler.OAuth2LoginSuccessHandler;
import com.myproject.ecommerce.security.jwt.JwtDecoderCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS_POST = {
            "/api/accounts",
            "/api/auth/login",
            "/api/auth/introspect",
            "/api/auth/logout"
    };

    private final String[] PUBLIC_ENDPOINTS_GET = {
            "/api/products/**",
            "/api/categories/**",
            "/api/auth/social-login"
    };

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final JwtDecoderCustom jwtDecoderCustom;

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(Customizer.withDefaults());

        httpSecurity.authorizeHttpRequests( request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS_POST).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS_GET).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/accounts").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated());

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .decoder(jwtDecoderCustom)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 401 Unauthorized Handling
                .accessDeniedHandler(jwtAccessDeniedHandler) // 403 Forbidden Handling
        );

        httpSecurity.oauth2Login(oauth -> oauth
                .successHandler(oAuth2LoginSuccessHandler)
        );

        return httpSecurity.build();
    }


    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){

        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }


    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public CorsFilter corsFilter(){

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of(
                "http://192.168.0.100:5500",
                "http://localhost:5500",
                "http://192.168.0.102:5500",
                "http://192.168.0.101:5500",
                "http://192.168.1.4:5500"
        ));
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}