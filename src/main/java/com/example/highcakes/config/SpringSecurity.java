package com.example.highcakes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/js/**", "/css/**", "/fonts/**", "/images/**", "/static/**")
                .permitAll()
                .requestMatchers("/manual", "/users", "/users/add", "/users/save",
                        "/users/{id}/edit", "/users/delete/{id}", "/save/cake", "/edit/cake",
                        "/delete/{id}", "/offer/delete/{id}", "/reviews/delete/{id}",
                        "/unique/save", "/unique/delete/{id}").hasAuthority("Администратор")
                .requestMatchers("/save/cake", "/edit/cake", "/delete/{id}").hasAuthority("Модератор каталога")
                .requestMatchers("/reviews/delete/{id}").hasAuthority("Модератор отзывов")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .permitAll()
                                .and()
                )
                .rememberMe()
                .key("my-super-secret-key")
                .tokenValiditySeconds(15000)
                .and()
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}