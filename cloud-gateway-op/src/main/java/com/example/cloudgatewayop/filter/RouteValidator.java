package com.example.cloudgatewayop.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {



    public static final List<String> openApiEndpoints = List.of(
      "/auth/register",
      "/auth/authenticate",
      "/auth/validate",
      "/eureka"

    );
public Predicate<ServerHttpRequest> isSecured =
        request -> openApiEndpoints
                .stream()
                .noneMatch(uri ->request.getURI().getPath().contains(uri));
}
