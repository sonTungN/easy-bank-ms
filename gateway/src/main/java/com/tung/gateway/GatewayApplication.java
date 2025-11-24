package com.tung.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(
                        predicateSpec ->
                                predicateSpec
                                        .path("/easybank/accountservice/**")
                                        .filters(f ->
                                                f
                                                        .rewritePath("/easybank/accountservice/(?<segment>.*)", "/${segment}")
                                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                        )
                                        .uri("lb://ACCOUNTSERVICE")
                )
                .route(
                        predicateSpec ->
                                predicateSpec
                                        .path("/easybank/loanservice/**")
                                        .filters(f ->
                                                f
                                                        .rewritePath("/easybank/loanservice/(?<segment>.*)", "/${segment}")
                                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                        )
                                        .uri("lb://LOANSERVICE")
                )
                .route(
                        predicateSpec ->
                                predicateSpec
                                        .path("/easybank/cardservice/**")
                                        .filters(f ->
                                                f
                                                        .rewritePath("/easybank/cardservice/(?<segment>.*)", "/${segment}")
                                                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                        )
                                        .uri("lb://CARDSERVICE")
                )
                .build();
    }
}
