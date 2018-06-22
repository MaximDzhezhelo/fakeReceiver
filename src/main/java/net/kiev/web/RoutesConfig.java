package net.kiev.web;

import net.kiev.web.handler.OrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class RoutesConfig {

    public static final String URL_NOTIFICATIONS = "/request";

    @Bean
    public RouterFunction routes(){
        final OrderHandler orderHandler = new OrderHandler();

        return route(POST(URL_NOTIFICATIONS),orderHandler::ok);
    }

}