package com.bank.report.services;

import com.bank.report.models.utils.ResponseActive;
import com.bank.report.models.utils.ResponsePasive;
import reactor.core.publisher.Mono;

public interface IPasiveService {
    Mono<ResponsePasive> getPasives();
}
