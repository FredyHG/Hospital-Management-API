package dev.dracarys.com.hospitalquerysystem.configs.security;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static dev.dracarys.com.hospitalquerysystem.configs.security.MyCustomDsl.customDsl;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/v1/hospital/appointments/**").hasAnyRole("DOCTOR", "HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/hospital/appointments/**").hasAnyRole("HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/hospital/appointments/**").hasAnyRole("HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/hospital/appointments/**").hasAnyRole("HEADNURSE", "ADMIN")
                .antMatchers(HttpMethod.GET, "/v1/hospital/stay/**").hasAnyRole("DOCTOR", "HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/hospital/stay/**").hasAnyRole("HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/hospital/stay/**").hasAnyRole("HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/hospital/stay/**").hasAnyRole("HEADNURSE", "ADMIN")
                .antMatchers(HttpMethod.GET, "/v1/hospital/doctor/**").hasAnyRole("HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.GET, "/v1/hospital/patients/**").hasAnyRole("HEADNURSE", "ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/hospital/patients/**").hasAnyRole("ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/hospital/patients/**").hasAnyRole("ATTENDANT", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/hospital/patients/**").hasAnyRole("HEADNURSE", "ADMIN")
                .anyRequest().hasRole("ADMIN")
                .and()
                .apply(customDsl())
                .and()
                .csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource1(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
