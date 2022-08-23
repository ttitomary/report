package com.bank.report.services;

import com.bank.report.models.utils.ResponseActive;
import reactor.core.publisher.Mono;

public interface IActiveService {
    Mono<ResponseActive> getActives();
}
