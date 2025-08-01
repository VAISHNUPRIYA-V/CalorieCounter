package com.example.CalorieCalcu.config;

import ch.qos.logback.core.encoder.Encoder;
import com.example.CalorieCalcu.jwt.JwtAuthenticationFilter;
import com.example.CalorieCalcu.services.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SpringConfig {
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config=new CorsConfiguration();
        // Ensure "http://localhost:5173" is the correct origin for your frontend
        config.setAllowedOriginPatterns(List.of("http://localhost:5173","https://calorie-counter-front-79zg.vercel.app/"));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf)->csrf.disable()) // CSRF disabled for API. Consider alternatives for browser-based clients if needed.
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth-> {
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            // Permit access to /api/auth/** AND /api/login AND /api/register (if it exists)
                            .requestMatchers("/login", "/api/auth/login", "/register", "/api/auth/register").permitAll();
                    auth.anyRequest().authenticated(); // All other requests require authentication
                })
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}







//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.
//                csrf((csrf)->csrf.disable())
//                .authorizeHttpRequests(auth-> {
//                    auth.requestMatchers(HttpMethod.POST,"/emp").hasRole("ADMIN");
//                    auth.requestMatchers(HttpMethod.PUT,"/emp").hasRole("ADMIN");
//                    auth.requestMatchers(HttpMethod.DELETE,"/emp").hasRole("ADMIN");
//                    auth.requestMatchers(HttpMethod.GET,"/**").hasAnyRole("ADMIN","USER");
//                    auth.anyRequest().authenticated();
//                })
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    InMemoryUserDetailsManager userDetails(){
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        UserDetails ksp= User.builder()
//                .username("ksp")
//                .password(passwordEncoder().encode("ksp"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(admin,ksp);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

