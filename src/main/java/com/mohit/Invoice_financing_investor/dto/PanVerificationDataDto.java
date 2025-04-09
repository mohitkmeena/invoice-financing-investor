package com.mohit.Invoice_financing_investor.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PanVerificationDataDto {
    @JsonProperty("@entity")
    private String entity;

    private String pan;
    private String category;
    private String status;
    private String remarks;

    @JsonProperty("name_as_per_pan_match")
    private boolean nameAsPerPanMatch;

    @JsonProperty("date_of_birth_match")
    private boolean dateOfBirthMatch;

    @JsonProperty("aadhaar_seeding_status")
    private String aadhaarSeedingStatus;
}