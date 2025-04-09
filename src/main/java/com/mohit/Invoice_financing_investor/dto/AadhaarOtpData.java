package com.mohit.Invoice_financing_investor.dto;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AadhaarOtpData {
    @Id
    private String investorId;

    private String reference_id;  // Optional
    private String message;       // Always present in data block
}
