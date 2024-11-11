package com.motivank.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class CustomRouteLocatorFactory {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p.path("/api/accounts/**")
                .filters(f -> f.rewritePath("/api/accounts/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/contact-support")))
                .uri("lb://ACCOUNTS"))
            .route(p -> p.path("/api/cards/**")
                .filters(f -> f.rewritePath("/api/cards/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/contact-support")))
                .uri("lb://CARDS"))
            .route(p -> p.path("/api/loans/**")
                .filters(f -> f.rewritePath("/api/loans/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/contact-support")))
                .uri("lb://LOANS"))
            .build();
    }
}
