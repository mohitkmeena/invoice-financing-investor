package com.mohit.Invoice_financing_investor.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InvestorUserDto {
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
}
