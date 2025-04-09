package com.mohit.Invoice_financing_investor.dto;

import lombok.Data;

@Data
public class AadhaarOtpResponseDto {
    private int code;
    private long timestamp;
    private String transaction_id;
    private String message;
    private AadhaarOtpData data;
}

