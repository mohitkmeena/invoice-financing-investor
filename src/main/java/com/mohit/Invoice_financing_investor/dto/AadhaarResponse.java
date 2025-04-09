package com.mohit.Invoice_financing_investor.dto;

import lombok.Data;

@Data
public class AadhaarResponse {
    private int code;
    private long timestamp;
    private String transaction_id;
    private String message; // optional - in some 422 or "under process" cases
    private AadhaarData data;
}
