package com.mohit.Invoice_financing_investor.service;

import com.mohit.Invoice_financing_investor.consumer.InvestorUserDto;
import org.springframework.stereotype.Service;

@Service
public interface InvestorService {
    public void saveInvestor(InvestorUserDto investorUserDto);
    public InvestorUserDto getInvestor(String investorId);
    public void updateInvestor(InvestorUserDto investorUserDto);
    public void deleteInvestor(String investorId);
}
