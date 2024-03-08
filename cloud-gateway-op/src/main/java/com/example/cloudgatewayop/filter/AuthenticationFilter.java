package com.example.cloudgatewayop.filter;

import com.example.cloudgatewayop.OpenFeign.IdentityClient;
import com.example.cloudgatewayop.Response.ErrorResponse;
import com.example.cloudgatewayop.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.ObjectInputFilter;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {



    @Autowired
    private RouteValidator validator;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    IdentityClient identityClient;

    @Autowired
    JwtUtil jwtUtil;
    public AuthenticationFilter() {
        super(Config.class);

    }

//    @Override
//    public GatewayFilter apply(Config config) {
//        return new GatewayFilter() {
//            @Override
//            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//                if (validator.isSecured.test(exchange.getRequest())) {
//                    if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
//                        throw new RuntimeException("Missing authorization header");
//                    }
//
//                    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                        authHeader = authHeader.substring(7);
//                    }
//                    try {
//                        /*
//                        Chỗ này bị lỗi block nên command lại
//                         */
//                   // identityClient.validate(authHeader);
//                        jwtUtil.validateToken(authHeader);
//                    }catch (Exception e){
//                        System.out.println("Invalid Acces");
//                        throw new RuntimeException("un authorized");
//                    }
//                }
//                return chain.filter(exchange);
//            }
//        };
//    }
@Override
public GatewayFilter apply(Config config) {
    return new GatewayFilter() {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
                    return errorResponse(exchange.getResponse(), HttpStatus.UNAUTHORIZED, "Missing authorization header", "UNAUTHORIZED");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    // Thực hiện xác thực token ở đây
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    return errorResponse(exchange.getResponse(), HttpStatus.UNAUTHORIZED, "Invalid Access", "UNAUTHORIZED");
                }
            }
            return chain.filter(exchange);
        }

        private Mono<Void> errorResponse(ServerHttpResponse response, HttpStatus status, String errorMessage, String errorCode) {
            ErrorResponse errorResponse = new ErrorResponse(errorMessage, errorCode, String.valueOf(status.value()));

            response.setStatusCode(status);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            try {
                String errorResponseJson = objectMapper.writeValueAsString(errorResponse);
                return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponseJson.getBytes())));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return Mono.error(e);
            }        }
    };
}
    public static class Config {
    }
}
