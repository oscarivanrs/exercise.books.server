package com.example.ecommerce.books.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Value("${security.jwt.disable}")
    private boolean sec_disable;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	if(sec_disable)
    		return http.csrf(csrf -> csrf.disable()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
        
    	return http.csrf(csrf -> csrf.disable())
    			.authorizeHttpRequests(request -> request.requestMatchers("/auth/**", "/books/reachable")
    	                .permitAll())
    	    			.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.OPTIONS, "/**")
    	                .permitAll()
    	                .anyRequest()
    	                .authenticated())
    	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    				.authenticationProvider(authenticationProvider)
    				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}