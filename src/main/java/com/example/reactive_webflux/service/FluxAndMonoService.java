package com.example.reactive_webflux.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public class FluxAndMonoService {
    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(List.of("Mango","Orange","Banana")).log(); //return 0-N and one by one
    }
    public Mono<String> fruitMono() {
        return Mono.just("Mango").log(); //return 0-1 and one by one
    }
    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .map(String::toUpperCase).log(); // doing action and one by one
    }
    public Flux<String> fruitsFluxFilter(int number) {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .filter(s -> s.length() > number); // filter action and one by one
    }
    public Flux<String> fruitsFluxFilterMap(int number) {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .filter(s -> s.length() > number)
                .map(String::toUpperCase);
    }
    public Flux<String> fruitsFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .flatMap(s -> Flux.just(s.split(""))
                        .delayElements(Duration.ofMillis(
                                new Random().nextInt(1000)
                        )))
                .log();
    }

}
