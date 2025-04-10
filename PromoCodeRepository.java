package com.hct.projects;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Integer> {
    PromoCode findByCode(String code);
    List<PromoCode> findByExpiryDateAfter(LocalDate currentDate);
    List<PromoCode> findByDiscountGreaterThan(double discountValue);
}
