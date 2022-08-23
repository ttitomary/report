package com.bank.report.models.utils;

import com.bank.report.models.documents.Active;
import lombok.Data;

import java.util.List;

@Data
public class ResponseActive {
    private List<Active> data;
    private String message;
    private String status;
}
