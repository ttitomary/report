package com.bank.report.models.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
public class Active {
    @Id
    private String id;
    private String clientId;
    private String activeType;
    private LocalDateTime dateRegister;
}
