package com.motivank.gatewayserver.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
public class CustomRouteLocatorFactory {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p.path("/api/accounts/**")
                .filters(f -> f.rewritePath("/api/accounts/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .circuitBreaker(config -> config.setName("accountsCircuitBreaker").setFallbackUri("forward:/contact-support"))) // Fallback to contact-support route on circuit open
                .uri("lb://ACCOUNTS"))
            .route(p -> p.path("/api/cards/**")
                .filters(f -> f.rewritePath("/api/cards/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .retry(retryConfig -> retryConfig.setRetries(3)
                        .setMethods(HttpMethod.GET) // Retry only on GET method, GET 메서드는 재시도 해도 side effect가 없기 때문
                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true) // backoff interval: 100ms, 1s, 2 times, exponential backoff
                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR))) // Retry 3 times on 500 error
                .uri("lb://CARDS"))
            .route(p -> p.path("/api/loans/**")
                .filters(f -> f.rewritePath("/api/loans/(?<segment>.*)", "/${segment}")
                    .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
                        .setKeyResolver(userKeyResolver())))
                .uri("lb://LOANS"))
            .build();
    }

    // Circuit Breaker Configuration, default timeout 4 seconds
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
            .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
            .build());
    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1, 1); // 1초에 1개의 요청을 허용하고 초과한 요청은 1초 동안 대기
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
            .defaultIfEmpty("annonymous");
    }

}
