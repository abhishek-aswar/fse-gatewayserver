package com.eaztbytes.gatewayserver;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}
	
	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes().route(p -> p.path("/cts-fse/commandapplication/**")
				.filters(f -> f.rewritePath("/cts-fse/commandapplication/(?<segment>.*)", "/${segment}")
						.addResponseHeader("X-Response-Time", new Date().toString()))
				.uri("lb://COMMANDAPPLICATION"))
				.route(p -> p.path("/cts-fse/queryapplication/**")
						.filters(f -> f.rewritePath("/cts-fse/queryapplication/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", new Date().toString()))
						.uri("lb://QUERYAPPLICATION"))
				.build();
	}

}
