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
                .map(String::toUpperCase); // filter action, doing action and one by one
    }

    public Flux<String> fruitsFluxFlatMap() {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .flatMap(s -> Flux.just(s.split("")))
                .log(); //like map, but it chance thread to another thread and can combine with another thread
    }

    public Flux<String> fruitsFluxFlatMapAsync() {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .flatMap(s -> Flux.just(s.split(""))
                        .delayElements(Duration.ofMillis(
                                new Random().nextInt(1000)
                        )))
                .log(); // doing action and run async
    }

    public Flux<String> fruitsFluxConcatMap() {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .concatMap(s -> Flux.just(s.split(""))
                        .delayElements(Duration.ofMillis(
                                new Random().nextInt(1000)
                        )))
                .log();
    }

    public Mono<List<String>> fruitMonoFlatMap() {
        return Mono.just("Mango")
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log();
    }

}
