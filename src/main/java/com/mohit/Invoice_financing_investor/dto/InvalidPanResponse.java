package com.mohit.Invoice_financing_investor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InvalidPanResponse {
    public String message;
    private String code;
    @JsonProperty("transaction_id")
    private String transactionId;
    private long timestamp;
}
