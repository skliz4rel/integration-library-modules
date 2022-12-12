package com.lms.api.http;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebclientUtitlity1 {

    public void get(){
        WebClient client = WebClient.create("http://localhost:8080");
        Flux<Object> flux= client
                .get()
                .uri("/invoice/allInvoices")
                .retrieve()
                .bodyToFlux(Object.class);
        flux.doOnNext(System.out::println).blockLast();
    }

    public void getOne(){
        WebClient client = WebClient.create("http://localhost:8080");
        Mono<Object> mono= client
                .get()
                .uri("/invoice/get/3")
                .retrieve()
                .bodyToMono(Object.class);
        mono.subscribe(System.out::println);
    }

    public void post(Object user){

        WebClient client = WebClient.create("http://localhost:8080");
        Mono<Object> mono= client
                .post()
                .uri("/invoice/save")
                .body(Mono.just(user),Object.class)
                .retrieve().bodyToMono(Object.class);

        mono.subscribe(System.out::println);
    }


    public void delete(){

        WebClient client = WebClient.create("http://localhost:8080");
        Mono<Void> mono= client
                .delete()
                .uri("/invoice/delete/3")
                .retrieve()
                .bodyToMono(Void.class);
        mono.subscribe(System.out::println);
        System.out.println("Invoice Deleted!");
    }

}