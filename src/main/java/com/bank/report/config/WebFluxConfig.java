package com.bank.report.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFlux
public class WebFluxConfig {
    @Value("${app.module.pasive.service.url}")
    private String urlPasive;

    @Value("${app.module.active.service.url}")
    private String urlActive;

    @Bean
    public WebClient getWebClientPasive()
    {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10))
                                        .addHandlerLast(new WriteTimeoutHandler(10))));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

        return WebClient.builder()
                .baseUrl(urlPasive)
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient getWebClientActive()
    {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10))
                                        .addHandlerLast(new WriteTimeoutHandler(10))));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

        return WebClient.builder()
                .baseUrl(urlActive)
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
