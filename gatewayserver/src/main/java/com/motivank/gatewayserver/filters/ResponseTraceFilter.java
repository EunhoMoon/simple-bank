package com.motivank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import static com.motivank.gatewayserver.filters.FilterUtility.*;

@Configuration
public class ResponseTraceFilter {

    private final FilterUtility filterUtility;

    private final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    public ResponseTraceFilter(FilterUtility filterUtility) {
        this.filterUtility = filterUtility;
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange)
            .then(Mono.fromRunnable(() -> {
                HttpHeaders headers = exchange.getRequest().getHeaders();
                String correlationId = filterUtility.getCorrelationId(headers);
                logger.debug("Response ID: {}", correlationId);
                exchange.getResponse().getHeaders().add(
                    CORRELATION_ID, correlationId);
            }));
    }

}
