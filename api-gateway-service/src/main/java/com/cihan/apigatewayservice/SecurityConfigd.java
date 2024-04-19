package com.cihan.apigatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


//@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfigd {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity, Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter) {
//        serverHttpSecurity.authorizeExchange(exchange -> exchange.anyExchange().authenticated())
//                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);

//        serverHttpSecurity.authorizeExchange(exchange -> exchange.pathMatchers("/api/product/user/**").hasRole("USER").
//                        anyExchange().authenticated())
//                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
//
//
//        serverHttpSecurity.csrf().disable();
//        return serverHttpSecurity.build();

        serverHttpSecurity.authorizeExchange()
                .pathMatchers("/api/product/**").hasAnyAuthority("USER")
                .pathMatchers("/api/order/**").hasAnyAuthority("ADMIN")
                .anyExchange().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);
        // @formatter:on

        return serverHttpSecurity.build();
    }







}
