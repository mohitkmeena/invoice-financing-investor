package com.mohit.Invoice_financing_investor.service;

import com.mohit.Invoice_financing_investor.consumer.InvestorUserDto;
import com.mohit.Invoice_financing_investor.dto.InvestorDto;
import com.mohit.Invoice_financing_investor.dto.ResponseDto;
import com.mohit.Invoice_financing_investor.dto.VerifyPanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface InvestorService {
    public void saveInvestor(InvestorUserDto investorUserDto);
    public ResponseEntity<InvestorDto> getInvestor(String investorId);
    public ResponseEntity<InvestorDto> updateInvestor(InvestorDto investorDto);
    public ResponseEntity<InvestorDto> deleteInvestor(String investorId);
    public ResponseEntity<ResponseDto> verifyPan(String investorId, VerifyPanDto verifyPanDto);


}
