package com.test.task.bank.config;

import com.test.task.bank.security.filter.JwtAuthUserFilter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EnableGlobalMethodSecurity(
        securedEnabled = true
)
public class SecurityConfig {
    JwtAuthUserFilter jwtAuthUserFilter;

    public SecurityConfig(JwtAuthUserFilter jwtAuthUserFilter) {
        this.jwtAuthUserFilter = jwtAuthUserFilter;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors(cors -> corsConfigurationSource())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers("/swagger-ui/**",
                        "/v3/**",
                        "/user/registration/**")
                .permitAll()
                .and()
                .addFilterBefore(jwtAuthUserFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
