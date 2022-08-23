package com.bank.report.models.utils;

import com.bank.report.models.documents.Pasive;
import lombok.Data;

import java.util.List;
@Data
public class ResponsePasive {
    private List<Pasive> data;
    private String message;
    private String status;
}
