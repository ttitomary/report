package com.bank.report.services.impl;

import com.bank.report.models.utils.ResponsePasive;
import com.bank.report.services.IPasiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PasiveService implements IPasiveService {
    @Autowired
    @Qualifier("getWebClientPasive")
    WebClient webClient;

    @Override
    public Mono<ResponsePasive> getPasives() {
        return webClient.get()
                .uri("/api/pasive")
                .retrieve()
                .bodyToMono(ResponsePasive.class);
    }
}
