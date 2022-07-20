package com.ndr.micro.gateway;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

public class FilterUtils
{
    public static String CORRELATIONAL_ID = "tmx-correlation-id";
    public static String AUTH_TOKEN = "tmx-auth-token";
    public static String USER_ID = "tmx-user-id";
    public static String ORG_ID = "tmx-org-id";
    public static String PRE_FILTER_TYPE = "pre";
    public static String POST_FILTER_TYPE = "post";
    public static String ROUTE_FILTER_TYPE = "route";

    public static String getCorrelationId(HttpHeaders requestHeaders)
    {
        if (requestHeaders.get(CORRELATIONAL_ID) != null)
        {
            List<String> header = requestHeaders.get(CORRELATIONAL_ID);
            return header.stream().findFirst().get();
        }

        return null;
    }

    public static ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value)
    {
        return exchange.mutate().request(
                exchange.getRequest().mutate()
                        .header(name, value)
                        .build())
                    .build();
    }

    public static ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId)
    {
        return FilterUtils.setRequestHeader(exchange, CORRELATIONAL_ID, correlationId);
    }

    
}
