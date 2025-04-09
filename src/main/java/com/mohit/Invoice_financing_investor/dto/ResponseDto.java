package com.mohit.Invoice_financing_investor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String message;
    private String code;
}
