package com.mohit.Invoice_financing_investor.dto;


import lombok.Data;

@Data
public class AadhaarData {

    private String reference_id;
    private String status;
    private String message;
    private String care_of;
    private String full_address;
    private String date_of_birth;
    private String email_hash;
    private String gender;
    private String name;
    private AadhaarAddress address;
    private String year_of_birth;
    private String mobile_hash;
    private String photo;
    private String share_code;
}