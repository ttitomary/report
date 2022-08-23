package com.bank.report.services.impl;

import com.bank.report.models.utils.ResponseActive;
import com.bank.report.services.IActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ActiveService implements IActiveService {
    @Autowired
    @Qualifier("getWebClientActive")
    WebClient webClient;

    @Override
    public Mono<ResponseActive> getActives() {
        return webClient.get()
                .uri("/api/active")
                .retrieve()
                .bodyToMono(ResponseActive.class);
    }
}
