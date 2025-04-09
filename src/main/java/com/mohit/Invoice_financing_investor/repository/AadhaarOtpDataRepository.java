package com.mohit.Invoice_financing_investor.repository;

import com.mohit.Invoice_financing_investor.dto.AadhaarOtpData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AadhaarOtpDataRepository extends JpaRepository<AadhaarOtpData,String > {
}
