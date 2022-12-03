package com.reactive.first.demo.controller;

import static com.reactive.first.demo.common.CustomScheduler.scheduleThis;
import static java.time.Duration.ofMillis;
import static reactor.core.publisher.Mono.defer;

import com.reactive.first.demo.mdoc.Hello;
import com.reactive.first.demo.repo.HelloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GreetingController {

    @Autowired
    HelloRepo helloRepo;


    @GetMapping("/hello/{id}")
    public Mono<Hello> hello(@PathVariable String id) {
        return scheduleThis(request())
            .timeout(ofMillis(5000))
            .responseFrom(discard -> defer(() -> getHello(id)));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/name")
    public Mono<Hello> name(@RequestBody Hello hello) {
        System.out.println("REQUEST :: " + hello);
        return helloRepo.save(hello);
    }

    private Mono<Hello> getHello(String id) {
        Mono<Hello> helloMono = helloRepo.findById(id);
        return helloMono;
    }

    private Mono<Boolean> request() {
        return Mono.just(true);
    }

}
