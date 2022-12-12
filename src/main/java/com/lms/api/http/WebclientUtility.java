package com.lms.api.http;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@AllArgsConstructor
public class WebclientUtility<T> {

    private final HttpClientUtil httpClientUtil;


    public Mono<Object> get(String endpoint, Map<String, String> headers, Class responseClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .get()
                .uri(endpoint)
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }

    public Mono<Object> getWithErrorClass(String endpoint, Map<String, String> headers, Class responseClass, Class errorClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .get()
                .uri(endpoint)
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.bodyToMono(errorClass).flatMap(
                                errorResponse -> {
                                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                    ObjectOutputStream out = null;
                                    XMLEncoder xmlEncoder = new XMLEncoder(bos);
                                    xmlEncoder.writeObject(errorResponse);
                                    byte[] errorBytes = bos.toByteArray();
                                    return Mono.error(new WebClientResponseException(clientResponse.rawStatusCode(), clientResponse.statusCode().getReasonPhrase(), null, errorBytes, null));
                                });
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }

    public Mono<Object> delete(String endpoint, Map<String, String> headers, Class responseClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .delete()
                .uri(endpoint)
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }

    public Mono<Object> delete(String endpoint, Object request, Map<String, String> headers, Class responseClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .method(HttpMethod.DELETE)
                .uri(endpoint)
                .body(BodyInserters.fromValue(request))
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }

    public Mono<Object> delete(String endpoint, Map<String, String> headers, Class responseClass, Class errorClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .delete()
                .uri(endpoint)
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }

    public Mono<Object> post(String endpoint, Object request, Map<String, String> headers, Class responseClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .post()
                .uri(endpoint)
                .body(BodyInserters.fromValue(request))
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }

    public Mono<Object> put(String endpoint, Object request, Map<String, String> headers, Class responseClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .put()
                .uri(endpoint)
                .body(BodyInserters.fromValue(request))
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }

    public Mono<Object> patch(String endpoint, Object request, Map<String, String> headers, Class responseClass) throws SSLException {

        WebClient webClient;
        webClient = createWebClient();

        return webClient
                .patch()
                .uri(endpoint)
                .body(BodyInserters.fromValue(request))
                .headers(httpHeaders -> {
                    httpHeaders.setAccept(getAcceptedMediaTypes());
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    if (null != headers)
                        headers.keySet().forEach(key -> httpHeaders.add(key, headers.get(key)));
                })
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        log.error("HttpStatusCode = {}", clientResponse.statusCode());
                        log.error("HttpHeaders = {}", clientResponse.headers().asHttpHeaders());
                        return clientResponse.createException()
                                .flatMap(Mono::error);
                    }
                    return clientResponse.bodyToMono(responseClass);
                });
    }


    ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n");
                //append clientRequest method and url
                clientRequest
                        .headers()
                        .forEach((name, values) -> values.forEach(value ->{ /* append header key/value */}));
                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }

    ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n");
                //append clientRequest method and url
                clientResponse
                        .headers();

                log.debug(sb.toString());
            }
            return Mono.just(clientResponse);
        });
    }

    private WebClient createWebClient() throws SSLException {

        HttpClient httpClient = httpClientUtil.createInsecureHttpClient();
        return WebClient
                .builder()
                .filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(logRequest());
                    exchangeFilterFunctions.add(logResponse());
                })
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(1024 * 1024 * 50))
                .build();
    }

    private List<MediaType> getAcceptedMediaTypes() {
        List<MediaType> mediaTypes = new ArrayList();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.ALL);
        return mediaTypes;
    }
}