package com.pragma.foodcourt.infrastructure.configuration;

import com.pragma.foodcourt.domain.spi.IJwtSecurityServicePort;
import com.pragma.foodcourt.infrastructure.configuration.securityfilter.JwtTokenValidator;
import com.pragma.foodcourt.infrastructure.helper.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final IJwtSecurityServicePort jwtSecurityServicePort;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(SecurityConstants.getPublicEndpoints()).permitAll();
                    http.requestMatchers(HttpMethod.POST, SecurityConstants.getAdminEndpoints()).hasRole(SecurityConstants.ADMIN_ROLE);
                    http.requestMatchers(SecurityConstants.getOwnerEndpoints()).hasRole(SecurityConstants.OWNER_ROLE);
                    http.requestMatchers(HttpMethod.GET, SecurityConstants.getCustomerEndpoints()).hasRole(SecurityConstants.CUSTOMER_ROLE);
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtSecurityServicePort), BasicAuthenticationFilter.class)
                .build();
    }
}
