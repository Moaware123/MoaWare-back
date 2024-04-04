package com.greedy.moaware.common.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.greedy.moaware.jwt.JwtAccessDeniedHandler;
import com.greedy.moaware.jwt.JwtAuthenticationEntryPoint;
import com.greedy.moaware.jwt.JwtFilter;

@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, 
			JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtFilter jwtFilter) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().antMatchers("/productimgs/**");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 return http
		         .csrf()
		         	.disable()
		         	.exceptionHandling()
		         	.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		         	.accessDeniedHandler(jwtAccessDeniedHandler)
		         .and()	
		         .sessionManagement()
		             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		         .and()
		         	 // 권한 체크
		             .authorizeRequests()
		             .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		             .antMatchers("/auth/**").permitAll()
		             .antMatchers(HttpMethod.GET, "/emp/**").permitAll()
//		             .antMatchers("/proj/**", "/project/**", "/task/**").hasRole("PROJECT")
		         .and()
		         	.cors()
		         .and()
		         	.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		         .build();
	}
	

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000" ));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-Type", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

