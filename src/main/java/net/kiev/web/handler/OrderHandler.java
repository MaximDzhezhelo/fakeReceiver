package net.kiev.web.handler;

import net.kiev.web.model.ValueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class OrderHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public Mono<ServerResponse> ok(ServerRequest serverRequest) {
        final String requestId = UUID.randomUUID().toString();
        return serverRequest
                .bodyToMono(ValueDto.class)
                .doOnNext(order -> log.info("get order request " + order))
                .map(i -> {
                    log.info("map 1 " + requestId);
                    return i;
                })
                .map(i -> {
                    log.info("map 2 " + requestId);
                    return i;
                })
                .map(i -> {
                    log.info("map 3 " + requestId);
                    return i;
                })
                .flatMap(i -> Mono.fromCallable(() -> executeLongMethod(i, requestId))
                        .subscribeOn(Schedulers.elastic())
                        .map(v -> {
                            log.info("map 5 " + requestId);
                            return v;
                        })
                        .flatMap(req -> ServerResponse.ok().build()))
                .flatMap(req -> ServerResponse.ok().build());
    }

    private ValueDto executeLongMethod(final ValueDto dto, final String requestId) {
        final long start = System.currentTimeMillis();
        try {
            log.info("start executeLongMethod. requestId:" + requestId);


            TimeUnit.MILLISECONDS.sleep(1500);

            return dto;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return dto;
        } finally {
            log.info("finish executeLongMethod requestId:" + requestId + " executed in " + (System.currentTimeMillis() - start) + "ms.");
        }
    }
}