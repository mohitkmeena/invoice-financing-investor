package com.mohit.Invoice_financing_investor.controller;
import com.mohit.Invoice_financing_investor.consumer.InvestorUserDto;
import com.mohit.Invoice_financing_investor.dto.InvestorDto;
import com.mohit.Invoice_financing_investor.dto.ResponseDto;
import com.mohit.Invoice_financing_investor.dto.VerifyPanDto;
import com.mohit.Invoice_financing_investor.service.InvestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("investor/v1/investors")
public class InvestorController {

    @Autowired
    private InvestorService investorService;


    // Get investor details
    @GetMapping("/{investorId}")
    public ResponseEntity<InvestorDto> getInvestor(@PathVariable String investorId) {
        return investorService.getInvestor(investorId);
    }


    @PutMapping("/update")
    public ResponseEntity<InvestorDto> updateInvestor(@RequestBody InvestorDto investorDto) {
        return investorService.updateInvestor(investorDto);
    }


    @DeleteMapping("/delete/{investorId}")
    public ResponseEntity<InvestorDto> deleteInvestor(@RequestHeader("X-User-Id") String investorId) {
        return investorService.deleteInvestor(investorId);
    }


    @PostMapping("/verify-pan/")
    public ResponseEntity<ResponseDto> verifyPan(
            @RequestHeader("X-User-Id") String investorId,
            @RequestBody VerifyPanDto verifyPanDto
    ) {
        return investorService.verifyPan(investorId, verifyPanDto);
    }
}
