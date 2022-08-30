package com.bank.report.controller;

import com.bank.report.handlers.ResponseHandler;
import com.bank.report.models.documents.Active;
import com.bank.report.models.documents.Pasive;
import com.bank.report.models.documents.Report;
import com.bank.report.models.utils.Utils;
import com.bank.report.services.IActiveService;
import com.bank.report.services.IPasiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/report")
public class ReportRestControllers {
    @Autowired
    private IPasiveService pasiveService;

    @Autowired
    private IActiveService activeService;

    private static final Logger log = LoggerFactory.getLogger(ReportRestControllers.class);

    @GetMapping("/{idClient}")
    public Mono<ResponseEntity<Object>> reportsByClient(@PathVariable String idClient)
    {
        log.info("[INI] Reports By Client");
        var pasiveList = pasiveService.getPasives()
                .flatMap(response -> {
                    log.info(response.getData().toString());
                    if(!response.getData().isEmpty()){
                        var list = response.getData()
                                .stream()
                                .filter(a -> a.getClientId().equals(idClient))
                                .map(r -> Report.builder()
                                        .idClient(r.getClientId())
                                        .idProduct(r.getId())
                                        .type(r.getPasivesType())
                                        .date(r.getCreatedDate())
                                        .build())
                                .collect(Collectors.toList());
                        return Mono.just(list);
                    }
                    else{
                        return Mono.just(Collections.emptyList());
                    }
                });
        log.info(pasiveList.toString());

        var activeList = activeService.getActives()
                .flatMap(response -> {
                    log.info(response.getData().toString());
                    if(!response.getData().isEmpty()){
                        var list = response.getData()
                                .stream()
                                .filter(a -> a.getClientId().equals(idClient))
                                .map(r -> Report.builder()
                                        .idClient(r.getClientId())
                                        .idProduct(r.getId())
                                        .type(r.getActiveType())
                                        .date(r.getDateRegister())
                                        .build())
                                .collect(Collectors.toList());
                        return Mono.just(list);
                    }
                    else{
                        return Mono.just(Collections.emptyList());
                    }
                });
        log.info(activeList.toString());

        var fluxPasive = pasiveList.flatMapMany(Flux::fromIterable);
        log.info(fluxPasive.toString());

        var fluxActive = activeList.flatMapMany(Flux::fromIterable);
        log.info(fluxActive.toString());

        var reportList = Flux.merge(fluxPasive,fluxActive);
        log.info(reportList.toString());

        return reportList.collectList()
                .flatMap(reports -> Mono.just(ResponseHandler.response("Done", HttpStatus.OK, reports)))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] Reports By Client"));
    }

    @GetMapping("/general")
    public Mono<ResponseEntity<Object>> reportsByDate(@Validated @RequestParam("idClient") String idClient, @Validated @RequestParam("max") String max, @Validated @RequestParam("min") String min)
    {
        LocalDateTime localDateTimeMin = Utils.parseLocalDate(min);
        LocalDateTime localDateTimeMax = Utils.parseLocalDate(max);

        log.info("[INI] Reports By Date");
        log.info(localDateTimeMin.toString());
        log.info(localDateTimeMax.toString());

        var pasiveList = pasiveService.getPasives()
                .flatMap(response -> {
                    log.info(response.getData().toString());
                    if(!response.getData().isEmpty()){
                        var list = response.getData()
                                .stream()
                                .filter(a -> Utils.BetweenDates(a.getCreatedDate(), localDateTimeMin, localDateTimeMax) && a.getClientId().equals(idClient))
                                .map(r -> Report.builder()
                                        .idClient(r.getClientId())
                                        .idProduct(r.getId())
                                        .type(r.getPasivesType())
                                        .date(r.getCreatedDate())
                                        .build())
                                .collect(Collectors.toList());
                        return Mono.just(list);
                    }
                    else{
                        return Mono.just(Collections.emptyList());
                    }
                });
        log.info(pasiveList.toString());

        var activeList = activeService.getActives()
                .flatMap(response -> {
                    log.info(response.getData().toString());
                    if(!response.getData().isEmpty()){
                        var list = response.getData()
                                .stream()
                                .filter(a -> Utils.BetweenDates(a.getDateRegister(), localDateTimeMin, localDateTimeMax) && a.getClientId().equals(idClient))
                                .map(r -> Report.builder()
                                        .idClient(r.getClientId())
                                        .idProduct(r.getId())
                                        .type(r.getActiveType())
                                        .date(r.getDateRegister())
                                        .build())
                                .collect(Collectors.toList());
                        return Mono.just(list);
                    }
                    else{
                        return Mono.just(Collections.emptyList());
                    }
                });
        log.info(activeList.toString());

        var fluxPasive = pasiveList.flatMapMany(Flux::fromIterable);
        log.info(fluxPasive.toString());

        var fluxActive = activeList.flatMapMany(Flux::fromIterable);
        log.info(fluxActive.toString());

        var reportList = Flux.merge(fluxPasive,fluxActive);
        log.info(reportList.toString());

        return reportList.collectList()
                .flatMap(reports -> Mono.just(ResponseHandler.response("Done", HttpStatus.OK, reports)))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] Reports By Date"));
    }
}
