package com.mohit.Invoice_financing_investor.service;

import com.mohit.Invoice_financing_investor.consumer.InvestorUserDto;
import com.mohit.Invoice_financing_investor.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InvestorService {
    public void saveInvestor(InvestorUserDto investorUserDto);
    public ResponseEntity<InvestorDto> getInvestor(String investorId);
    public ResponseEntity<InvestorDto> updateInvestor(InvestorDto investorDto);
    public ResponseEntity<InvestorDto> deleteInvestor(String investorId);
    public ResponseEntity<ResponseDto> verifyPan(String investorId, VerifyPanDto verifyPanDto);
    public ResponseEntity<ResponseDto> verifyAadhar(String investorId, VerifyAadharDto verifyAadharDto);
    public ResponseEntity<ResponseDto> verifyAadhaarOtp(String investorId, VerifyOtpDto verifyOtpDto);

}
