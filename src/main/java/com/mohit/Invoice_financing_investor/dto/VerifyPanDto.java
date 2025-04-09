package com.mohit.Invoice_financing_investor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyPanDto {
    private String panCard;
    private String name_as_per_pan;
    private String date_of_birth;
    private String consent="Y";
    private String reason="verifying pan card for onboarding";

}
