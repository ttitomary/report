package com.bank.report.models.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Pasive {
    @Id
    private String id;
    private String clientId;
    private String pasivesType;
    private LocalDateTime dateRegister;
}
