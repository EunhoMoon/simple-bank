package com.motivank.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRouteLocatorFactory {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p.path("/api/accounts/**")
                .filters(f -> f.rewritePath("/api/accounts/(?<segment>.*)", "/${segment}"))
                .uri("lb://ACCOUNTS"))
            .route(p -> p.path("/api/cards/**")
                .filters(f -> f.rewritePath("/api/cards/(?<segment>.*)", "/${segment}"))
                .uri("lb://CARDS"))
            .route(p -> p.path("/api/loans/**")
                .filters(f -> f.rewritePath("/api/loans/(?<segment>.*)", "/${segment}"))
                .uri("lb://LOANS"))
            .build();
    }
}
