package com.example.reactive_webflux.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoServiceTest {
    FluxAndMonoService fluxAndMonoService=new FluxAndMonoService();
    @Test
    void fruitsFlux() {
        var fruitsFlux=fluxAndMonoService.fruitsFlux();
        StepVerifier.create(fruitsFlux).expectNext("Mango","Orange","Banana").verifyComplete();
    }

    @Test
    void fruitMono() {
        var fruitsFlux=fluxAndMonoService.fruitMono();
        StepVerifier.create(fruitsFlux).expectNext("Mango").verifyComplete();
    }

    @Test
    void fruitsFluxMap() {
        var fruitsFlux=fluxAndMonoService.fruitsFluxMap();
        StepVerifier.create(fruitsFlux).expectNext("MANGO","ORANGE","BANANA").verifyComplete();
    }

    @Test
    void fruitsFluxFilter() {
        var fruitsFlux = fluxAndMonoService.fruitsFluxFilter(5).log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Orange","Banana")
                .verifyComplete();
    }
    @Test
    void fruitsFluxFilterMap() {
        var fruitsFlux = fluxAndMonoService.fruitsFluxFilterMap(5);
        StepVerifier.create(fruitsFlux)
                .expectNext("ORANGE","BANANA")
                .verifyComplete();
    }
    @Test
    void fruitsFluxFlatMap() {
        var fruitsFlux = fluxAndMonoService.fruitsFluxFlatMap();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }
    @Test
    void fruitsFluxFlatMapAsync() {
        var fruitsFlux = fluxAndMonoService.fruitsFluxFlatMapAsync();
        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatMap() {

        var fruitsFlux = fluxAndMonoService.fruitsFluxConcatMap();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitMonoFlatMap() {
        var fruitsFlux = fluxAndMonoService.fruitMonoFlatMap();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
}