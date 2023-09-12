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
    void fruitsFluxTransform() {

        var fruitsFlux
                = fluxAndMonoService.fruitsFluxTransform(5);

        StepVerifier.create(fruitsFlux)
                .expectNext("Orange","Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {
        var fruitsFlux
                = fluxAndMonoService.fruitsFluxTransformDefaultIfEmpty(10);

        StepVerifier.create(fruitsFlux)
                .expectNext("Default")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformSwitchIfEmpty() {
        var fruitsFlux
                = fluxAndMonoService.fruitsFluxTransformSwitchIfEmpty(8);

        StepVerifier.create(fruitsFlux)
                .expectNext("Jack Fruit")
                .verifyComplete();

    }


    @Test
    void fruitMonoFlatMap() {
        var fruitsFlux = fluxAndMonoService.fruitMonoFlatMap();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(1)
                .verifyComplete();
    }
    @Test
    void fruitMonoFlatMapMany() {
        var fruitsFlux = fluxAndMonoService.fruitMonoFlatMapMany();

        StepVerifier.create(fruitsFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcat() {

        var fruitsFlux = fluxAndMonoService.fruitsFluxConcat().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mango","Orange","Tomato","Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatWith() {
        var fruitsFlux = fluxAndMonoService.fruitsFluxConcatWith().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mango","Orange","Tomato","Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMerge() {
        var fruitsFlux = fluxAndMonoService.fruitsFluxMerge().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mango","Tomato","Orange","Lemon")
                .verifyComplete();
    }
    @Test
    void fruitsFluxMergeWith() {
        var fruitsFlux = fluxAndMonoService.fruitsFluxMergeWith().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mango","Tomato","Orange","Lemon")
                .verifyComplete();
    }
    @Test
    void fruitsFluxMergeWithSequential() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxMergeWithSequential().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("Mango","Orange","Tomato","Lemon")
                .verifyComplete();
    }
    @Test
    void fruitsFluxZip() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxZip().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomato","OrangeLemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilterDoOn() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxFilterDoOn(5).log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Orange","Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorReturn() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxOnErrorReturn().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("Apple","Mango","Orange")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZipWith() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxZipWith().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomato","OrangeLemon")
                .verifyComplete();
    }
    @Test
    void fruitsFluxZipTuple() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxZipTuple().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("MangoTomatoPotato","OrangeLemonBeans")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorContinue() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxOnErrorContinue().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE","ORANGE")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorMap() {
        var fruitsFlux = fluxAndMonoService
                .fruitsFluxOnErrorMap().log();

        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE")
                .expectError(IllegalStateException.class)
                .verify();
    }

}