package com.example.reactive_webflux.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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
                .log(); //like map, but it runs async
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
                .log(); //run async, but it still sure one by one like queue
    }

    public Flux<String> fruitsFluxTransform(int number) {

        Function<Flux<String>,Flux<String>> filterData
                = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .transform(filterData)
                .log();
        //.filter(s -> s.length() > number);
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number) {

        Function<Flux<String>,Flux<String>> filterData
                = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .transform(filterData)
                .defaultIfEmpty("Default")
                .log(); //if fail, return default value

    }

    public Flux<String> fruitsFluxTransformSwitchIfEmpty(int number) {

        Function<Flux<String>,Flux<String>> filterData
                = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .transform(filterData)
                .switchIfEmpty(Flux.just("Pine","Jack Fruit")
                        .transform(filterData))
                .log(); //if fail, try another value

    }
    public Flux<String> fruitsFluxConcat() {
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        return Flux.concat(fruits,veggies); //concat Flux and one by one
    }

    public Flux<String> fruitsFluxConcatWith() {
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        return fruits.concatWith(veggies);
    }

    public Flux<String> fruitsFluxMerge() {
        var fruits = Flux.just("Mango","Orange")
                .delayElements(Duration.ofMillis(50));
        var veggies = Flux.just("Tomato","Lemon")
                .delayElements(Duration.ofMillis(75));

        return Flux.merge(fruits,veggies); //concat Flux and don't sure one by one
    }

    public Flux<String> fruitsFluxMergeWith() {
        var fruits = Flux.just("Mango","Orange")
                .delayElements(Duration.ofMillis(50));
        var veggies = Flux.just("Tomato","Lemon")
                .delayElements(Duration.ofMillis(75));

        return fruits.mergeWith(veggies);
    }

    public Flux<String> fruitsFluxMergeWithSequential() {
        var fruits = Flux.just("Mango","Orange")
                .delayElements(Duration.ofMillis(50));
        var veggies = Flux.just("Tomato","Lemon")
                .delayElements(Duration.ofMillis(75));

        return Flux.mergeSequential(fruits,veggies); //like concat
    }
    public Flux<String> fruitsFluxZip() {
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        return Flux.zip(fruits,veggies,
                (first,second) -> first+second).log(); //combine two Flux by custom way
    }

    public Flux<String> fruitsFluxZipWith() {
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        return fruits.zipWith(veggies,
                (first,second) -> first+second).log();
    }
    public Flux<String> fruitsFluxZipTuple() {
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");
        var moreVeggies = Flux.just("Potato","Beans");

        return Flux.zip(fruits,veggies,moreVeggies)
                .map(objects -> objects.getT1() + objects.getT2() + objects.getT3());
    }
    public Flux<String> fruitsFluxFilterDoOn(int number) {
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .filter(s -> s.length() > number)
                .doOnNext(s -> {
                    System.out.println("s = " + s);
                })
                .doOnSubscribe(subscription -> {
                    System.out.println("subscription.toString() = " + subscription.toString());
                })
                .doOnComplete(() -> System.out.println("Completed!!!"));
    }

    public Flux<String> fruitsFluxOnErrorReturn() {
        return Flux.just("Apple","Mango")
                .concatWith(Flux.error(
                        new RuntimeException("Exception Occurred")
                )).concatWith(Flux.just("Ola"))
                .onErrorReturn("Orange"); //replace error Flux with a fallBack value and exits function
    }

    public Flux<String> fruitsFluxOnErrorContinue() {
        return Flux.just("Apple","Mango","Orange")
                .map(s -> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception Occurred");
                    return s.toUpperCase(); //adopted error and continue function
                })
                .onErrorContinue((e,f) -> {
                    System.out.println("e = " + e);
                    System.out.println("f = " + f);
                });
    }

    public Flux<String> fruitsFluxOnErrorMap() {
        return Flux.just("Apple","Mango","Orange")
                .checkpoint("Error Checkpoint1")
                .map(s -> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception Occurred");
                    return s.toUpperCase();
                })
                .checkpoint("Error Checkpoint2")
                .onErrorMap(throwable -> {
                    System.out.println("throwable = " + throwable);
                    return new IllegalStateException("From onError Map");
                });
    }


    public Mono<List<String>> fruitMonoFlatMap() {
        return Mono.just("Mango")
                .flatMap(s -> Mono.just(List.of(s.split(""))))
                .log(); //return any value
    }
    public Flux<String> fruitMonoFlatMapMany() {
        return Mono.just("Mango")
                .flatMapMany(s -> Flux.just(s.split("")))
                .log(); //return Flux
    }


}
