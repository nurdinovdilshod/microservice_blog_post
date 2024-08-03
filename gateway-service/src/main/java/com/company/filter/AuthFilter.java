package com.company.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final RouteValidator routeValidator;
    private final RestTemplate restTemplate;

    public AuthFilter(RouteValidator routeValidator, RestTemplate restTemplate) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.restTemplate = restTemplate;
    }

    private static String getString(ServerHttpRequest httpRequest) {
        HttpHeaders httpHeaders = httpRequest.getHeaders();
        if (!httpHeaders.containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new RuntimeException("Missing authorization information");
        }
        List<String> list = httpHeaders.get(HttpHeaders.AUTHORIZATION);
        String authHeaders = Objects.requireNonNull(list).get(0);
        if (!Objects.isNull(authHeaders) && authHeaders.startsWith("Bearer ")) {
            authHeaders = authHeaders.substring(7);
        }
        return authHeaders;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest httpRequest = exchange.getRequest();
            if (routeValidator.isSecured.test(httpRequest)) {
                String authHeaders = getString(httpRequest);
                try {
                    Boolean isValid = restTemplate.getForObject("http://localhost:7070/api/v1/auth/token/validate?token=" + authHeaders, Boolean.class);
                    if (!Boolean.TRUE.equals(isValid)) {
                        throw new RuntimeException("Token is not valid");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Invalid token");
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Put the configuration properties for your filter here
    }
}
