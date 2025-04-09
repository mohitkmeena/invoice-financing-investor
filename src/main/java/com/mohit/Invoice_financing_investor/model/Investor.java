package com.mohit.Invoice_financing_investor.model;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class Investor  {
    @Id
    private String investorId;
    private String investorName;
    @Size(min = 10,max = 10,message = "please enter valid pan card  number")
    @Column(unique = true)
    private String pancard;
    @Size(min = 12,max = 12,message = "please enter a valid aadhar number")
    @Column(unique = true)
    private String aadharCard;
    private String nomineeName;
    @Size(min = 12,max = 12,message = "please enter a valid aadhar number")
    private String nomineeAaharCard;
    @Email(message = "enter valid email")
    @Column(unique = true)
    private String email;
    @Size(min =10,max = 10,message = "enter valid phone number")
    private String phoneNumber;
    private String address;
    @Column(unique = true)
    private String bankAccountNumber;
    private String bankName;
    private String ifscCode;

    private boolean isIdVerified;


}

