package com.mohit.Invoice_financing_investor.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohit.Invoice_financing_investor.consumer.InvestorUserDto;
import com.mohit.Invoice_financing_investor.dto.*;
import com.mohit.Invoice_financing_investor.model.Investor;
import com.mohit.Invoice_financing_investor.repository.AadhaarOtpDataRepository;
import com.mohit.Invoice_financing_investor.repository.InvestorRepository;
import com.mohit.Invoice_financing_investor.service.InvestorService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
@Service
public class InvestorServiceImpl implements InvestorService {

    @Value("${sandbox.api.key}")
    private String apiKey;
    @Autowired private AadhaarOtpDataRepository aadhaarOtpDataRepository;

    @Autowired private InvestorRepository investorRepository;
    @Override
    public void saveInvestor(InvestorUserDto investorUserDto) {
        Investor investor=investorUserDtoToInvestor(investorUserDto);
        investorRepository.save(investor);
    }

    @Override
    public ResponseEntity<InvestorDto> getInvestor(String investorId) {
        Investor investor= investorRepository.findByEmail(investorId);
        return  ResponseEntity.ok(investorToInvestorDto(investor));
    }

    @Override
    public ResponseEntity<InvestorDto> updateInvestor(InvestorDto investorDto) {

        Investor investor = investorRepository.findByEmail(investorDto.getEmail());

        if (!investor.isIdVerified()) {
            if (investorDto.getAadharCard() != null) {
                investor.setAadharCard(investorDto.getAadharCard());
            }
            if (investorDto.getPancard() != null) {
                investor.setPancard(investorDto.getPancard());
            }
        }


        investor.setNomineeName(investorDto.getNomineeName());
        investor.setNomineeAaharCard(investorDto.getNomineeAaharCard());
        investor.setPhoneNumber(investorDto.getPhoneNumber());
        investor.setAddress(investorDto.getAddress());
        investor.setBankAccountNumber(investorDto.getBankAccountNumber());
        investor.setBankName(investorDto.getBankName());
        investor.setIfscCode(investorDto.getIfscCode());


        investorRepository.save(investor);


        InvestorDto updatedDto = investorToInvestorDto(investor);
        return ResponseEntity.ok(updatedDto);
    }


    @Override
    public ResponseEntity<InvestorDto> deleteInvestor(String investorId) {
        // Check if investor exists
        Investor investor = investorRepository.findByEmail(investorId);


        InvestorDto deletedInvestorDto = investorToInvestorDto(investor);


        investorRepository.delete(investor);


        return ResponseEntity.ok(deletedInvestorDto);
    }


    @Override
    public ResponseEntity<ResponseDto> verifyPan(String investorId, VerifyPanDto verifyPanDto) {
        boolean isPanCardExist=investorRepository.existsByPanCardAndIsPanVerified(verifyPanDto.getPanCard());
        if(isPanCardExist){
            return new ResponseEntity(ResponseDto.builder()
                    .code("200")
                    .message("pancard already linked with some other user")
                    .build(), HttpStatus.OK);
        }
        Investor investor=investorRepository.findByEmail(investorId);
        try {
            OkHttpClient client = new OkHttpClient();
            String json = objectsTojson(verifyPanDto);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url("https://api.sandbox.co.in/kyc/pan/verify")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("x-accept-cache", "true")
                    .addHeader("content-type", "application/json")
                    .addHeader("x-api-key",apiKey)
                    .addHeader("authorization","")

                    .build();

            Response response = client.newCall(request).execute();
            ObjectMapper objectMapper=new ObjectMapper();
            if(response.code()!=200){
                InvalidPanResponse invalidPanResponse=objectMapper.readValue(response.body().string(),InvalidPanResponse.class);
                return new ResponseEntity(ResponseDto.builder()
                        .code("200")
                        .message("please enter a valid pan number")
                        .build(), HttpStatus.NOT_FOUND);
            }

            PanVerificationResponseDto panVerificationResponseDto=objectMapper.readValue(response.body().string(),PanVerificationResponseDto.class);
            if(panVerificationResponseDto.getData().isNameAsPerPanMatch()&&panVerificationResponseDto.getData().getStatus().equals("success")){
                investor.setPancard(verifyPanDto.getPanCard());
                investor.setPanVerified(true);
                investorRepository.save(investor);
                return new ResponseEntity(ResponseDto.builder()
                        .code("200")
                        .message("pan verification successful")
                        .build(), HttpStatus.OK);
            }
            else{
                if(!panVerificationResponseDto.getData().isNameAsPerPanMatch()){
                    return new ResponseEntity(ResponseDto.builder()
                            .code("422")
                            .message(" name entered && name in pan card do not match")
                            .build(), HttpStatus.OK);
                }else{
                    return new ResponseEntity(ResponseDto.builder()
                            .code("422")
                            .message(" Pan Verification failed")
                            .build(), HttpStatus.OK);
                }
            }

        }
        catch (IOException e){
            return new ResponseEntity(ResponseDto.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                    .message("pan verification failed")
                    .build(), HttpStatus.OK);
        }

    }
        private Investor investorUserDtoToInvestor (InvestorUserDto investorUserDto){
            return Investor.builder()
                    .email(investorUserDto.getEmail())
                    .phoneNumber(investorUserDto.getPhoneNumber())
                    .build();

        }
        private InvestorDto investorToInvestorDto (Investor investor){
            return InvestorDto.builder()
                    .investorName(investor.getInvestorName())
                    .pancard(investor.getPancard())
                    .aadharCard(investor.getAadharCard())
                    .nomineeName(investor.getNomineeName())
                    .nomineeAaharCard(investor.getNomineeAaharCard())
                    .email(investor.getEmail())
                    .phoneNumber(investor.getPhoneNumber())
                    .address(investor.getAddress())
                    .bankAccountNumber(investor.getBankAccountNumber())
                    .bankName(investor.getBankName())
                    .ifscCode(investor.getIfscCode())
                    .build();
        }



    private String objectsTojson(Object object){
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
   public ResponseEntity<ResponseDto> verifyAadhar(String investorId, VerifyAadharDto verifyAadharDto) {
        try {
            OkHttpClient client = new OkHttpClient();

            String json = objectsTojson(verifyAadharDto);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);

            Request request = new Request.Builder()
                    .url("https://api.sandbox.co.in/kyc/aadhaar/okyc/otp")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("x-api-version", "2.0")
                    .addHeader("content-type", "application/json")
                    .addHeader("x-api-key", apiKey)
                    .addHeader("authorization", "") // add bearer token if needed
                    .build();

            Response response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();

            if (response.code() != 200) {

                return new ResponseEntity<>(ResponseDto.builder()
                        .code("400")
                        .message("Aadhar verification initiation failed,Enter valid aadhar " )
                        .build(), HttpStatus.BAD_REQUEST);
            }


            String responseBody = response.body().string();
           AadhaarOtpResponseDto otpResponse = objectMapper.readValue(responseBody, AadhaarOtpResponseDto.class);
           if(Objects.equals(otpResponse.getData().getMessage(),"Invalid Aadhaar Card")){
               return new ResponseEntity<>(ResponseDto.builder()
                       .code("404")
                       .message("Aadhar verification  failed,Enter valid aadhar " )
                       .build(), HttpStatus.NOT_FOUND);
           }
           else if(Objects.equals(otpResponse.getMessage(),"OTP generated for this aadhaar, please try after 45 seconds")){
                return new ResponseEntity<>(ResponseDto.builder()
                        .code("400")
                        .message("OTP generated for this aadhaar, please try after 45 seconds " )
                        .build(), HttpStatus.BAD_REQUEST);
            }
           else if(Objects.equals(otpResponse.getData().getMessage(),"Aadhaar not linked to mobile number")){
                return new ResponseEntity<>(ResponseDto.builder()
                        .code("400")
                        .message("Aadhaar not linked to mobile number " )
                        .build(), HttpStatus.BAD_REQUEST);
            }
           else{
               AadhaarOtpData aadhaarOtpData=otpResponse.getData();
               aadhaarOtpData.setInvestorId(investorId);
               aadhaarOtpDataRepository.save(aadhaarOtpData);
           }


            // Save or use the transaction ID from response if needed for OTP confirmation later
            return new ResponseEntity<>(ResponseDto.builder()
                    .code("200")
                    .message("OTP sent successfully to your registered Aadhar number.")
                    .build(), HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .code("500")
                    .message("Internal Server Error while verifying Aadhar: " + e.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<ResponseDto> verifyAadhaarOtp(String investorId,VerifyOtpDto verifyOtpDto){
        try {
            OkHttpClient client = new OkHttpClient();

            String json = objectsTojson(verifyOtpDto);
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, json);

            Request request = new Request.Builder()
                    .url("https://api.sandbox.co.in/kyc/aadhaar/okyc/otp/verify")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("x-api-version", "2.0")
                    .addHeader("content-type", "application/json")
                    .addHeader("x-api-key", apiKey)
                    .addHeader("authorization", "") // add bearer token if needed
                    .build();

            Response response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();

            if (response.code() != 200) {

                return new ResponseEntity<>(ResponseDto.builder()
                        .code("400")
                        .message("Aadhar verification initiation failed,Enter valid aadhar " )
                        .build(), HttpStatus.BAD_REQUEST);
            }


            String responseBody = response.body().string();
            AadhaarOtpResponseDto otpResponse = objectMapper.readValue(responseBody, AadhaarOtpResponseDto.class);
            if(Objects.equals(otpResponse.getData().getMessage(),"Invalid Aadhaar Card")){
                return new ResponseEntity<>(ResponseDto.builder()
                        .code("404")
                        .message("Aadhar verification  failed,Enter valid aadhar " )
                        .build(), HttpStatus.NOT_FOUND);
            }
            else if(Objects.equals(otpResponse.getMessage(),"OTP generated for this aadhaar, please try after 45 seconds")){
                return new ResponseEntity<>(ResponseDto.builder()
                        .code("400")
                        .message("OTP generated for this aadhaar, please try after 45 seconds " )
                        .build(), HttpStatus.BAD_REQUEST);
            }
            else if(Objects.equals(otpResponse.getData().getMessage(),"Aadhaar not linked to mobile number")){
                return new ResponseEntity<>(ResponseDto.builder()
                        .code("400")
                        .message("Aadhaar not linked to mobile number " )
                        .build(), HttpStatus.BAD_REQUEST);
            }
            else{
                AadhaarOtpData aadhaarOtpData=otpResponse.getData();
                aadhaarOtpData.setInvestorId(investorId);
                aadhaarOtpDataRepository.save(aadhaarOtpData);
            }


            // Save or use the transaction ID from response if needed for OTP confirmation later
            return new ResponseEntity<>(ResponseDto.builder()
                    .code("200")
                    .message("OTP sent successfully to your registered Aadhar number.")
                    .build(), HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(ResponseDto.builder()
                    .code("500")
                    .message("Internal Server Error while verifying Aadhar: " + e.getMessage())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
