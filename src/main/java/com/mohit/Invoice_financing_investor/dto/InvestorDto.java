package com.mohit.Invoice_financing_investor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestorDto{



   private String investorName;

    private String pancard;
    private String aadharCard;
    private String nomineeName;
    private String nomineeAaharCard;
    private String email;
    private String phoneNumber;
    private String address;
    private String bankAccountNumber;
    private String bankName;
    private String ifscCode;
}
