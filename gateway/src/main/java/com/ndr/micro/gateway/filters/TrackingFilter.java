package com.ndr.micro.gateway.filters;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import com.ndr.micro.gateway.FilterUtils;

import reactor.core.publisher.Mono;

// @Order(1)
// @Component
public class TrackingFilter implements GlobalFilter{
    
    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        if (isCorrelationIdPresent(requestHeaders))
        {
            logger.info("tmx-correlation-id found in tracking filter? {}.",
                    FilterUtils.getCorrelationId(requestHeaders));
        } else
        {
            String correlationId = generateCorrelationId();
            exchange = FilterUtils.setCorrelationId(exchange, correlationId);

            logger.info("tmx-correlation-id generated in tracking filter: {}.", correlationId);
        }

        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders)
    {
        if (FilterUtils.getCorrelationId(requestHeaders) != null)
        {
            return true;
        }

        return false;
    }

    private String generateCorrelationId()
    {
        return UUID.randomUUID().toString();
    }


    

}
