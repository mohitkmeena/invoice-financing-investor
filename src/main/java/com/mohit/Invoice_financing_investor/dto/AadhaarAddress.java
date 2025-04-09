package com.mohit.Invoice_financing_investor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AadhaarAddress {
    @JsonProperty("@entity")
    private String entity;
    private String country;
    private String district;
    private String house;
    private String landmark;
    private String pincode;
    private String post_office;
    private String state;
    private String street;
    private String subdistrict;
    private String vtc;
}