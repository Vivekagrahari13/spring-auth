package com.security.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user1 = User.withUsername("Vivek")
                .password(passwordEncoder().encode("abc123"))
                .roles("USER")
                .build();

        UserDetails user2= User.withUsername("Kaustubh")
                .password(passwordEncoder().encode("xyz123"))
                .roles("USER")
                .build();

        UserDetails user3= User.withUsername("Gogo")
                .password(passwordEncoder().encode("gogo123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1,user2,user3);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorize->{authorize
//                .requestMatchers("/auth-app/admin").hasRole("ADMIN")
//                .requestMatchers("/auth-app/user").hasRole("USER")
                .anyRequest().authenticated();
    }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
}
