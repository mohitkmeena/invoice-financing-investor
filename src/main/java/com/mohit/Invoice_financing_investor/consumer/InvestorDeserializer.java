package com.mohit.Invoice_financing_investor.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohit.Invoice_financing_investor.dto.InvestorDto;
import jakarta.websocket.OnError;
import org.apache.kafka.common.serialization.Deserializer;

public class InvestorDeserializer implements Deserializer<InvestorUserDto> {
    @Override
    public InvestorUserDto deserialize(String arg1, byte[] data) {
        InvestorUserDto investorInfo=null;

        ObjectMapper objectMapper=new ObjectMapper();
        try{
            investorInfo=objectMapper.readValue(arg1, InvestorUserDto.class);

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return investorInfo;

    }



}
