package com.mohit.Invoice_financing_investor.dto;

import lombok.Data;

@Data
public class VerifyAadharDto {
    private String aadhaarNumber;
    private String consent="y";
    private String reason ="for kyc";
}
