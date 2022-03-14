package com.example.demo.filter;

import com.example.demo.model.AppUserDetails;
import com.example.demo.utils.JWTUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (this.isAuthMissing(request))
            return this.onError(exchange, "Authorization header is missing in request");

        final String token = this.getAuthHeader(request);

        AppUserDetails userDetails = JWTUtil.parseToken(token);
        
        if(userDetails==null) return this.onError(exchange, "Authorization header is invalid");

        this.populateRequestWithHeaders(exchange, userDetails);

        return chain.filter(exchange);
    }


    /*PRIVATE*/

    private Mono<Void> onError(ServerWebExchange exchange, String err) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        String token =  request.getCookies().getFirst("Auth").getValue();
        if(!token.startsWith("Bearer_")) return "null";
        return token.substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getCookies().containsKey("Auth");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, AppUserDetails userDetails) {
        exchange.getRequest().mutate()
                .header("username", userDetails.getUsername())
                .header("role", userDetails.getRole())
                .header("email", userDetails.getEmail())
                .build();
    }
}