package com.mohit.Invoice_financing_investor.repository;

import com.mohit.Invoice_financing_investor.dto.InvestorDto;
import com.mohit.Invoice_financing_investor.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorRepository extends JpaRepository<Investor ,String> {
    Investor findByEmail(String investorId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.panCard = :panCard AND c.isPanVerified = true")
    boolean existsByPanCardAndIsPanVerified(@Param("panCard") String panCard);

}
