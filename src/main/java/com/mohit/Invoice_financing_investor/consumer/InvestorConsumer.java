package com.mohit.Invoice_financing_investor.consumer;

import com.mohit.Invoice_financing_investor.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InvestorConsumer {
    @Autowired
    private InvestorService investorService;
    @KafkaListener(topics = "${spring.kafka.investor-topic-name}",groupId = "${spring.kafka.consumer.group-id}")
    public void consume(InvestorUserDto investorUserDto){
          investorService.saveInvestor(investorUserDto);
        System.out.println("Received Investor User Info: "+investorUserDto);
    }
}
