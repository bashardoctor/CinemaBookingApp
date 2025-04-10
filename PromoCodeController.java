package com.hct.projects;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promocodes")
public class PromoCodeController {

    @Autowired
    private PromoCodeRepository promoCodeRepository;
    
    // Get all promo codes
    @GetMapping
    public ResponseEntity<List<PromoCode>> getAllPromoCodes() {
        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        return new ResponseEntity<>(promoCodes, HttpStatus.OK);
    }
    
    // Get promo code by ID
    @GetMapping("/{id}")
    public ResponseEntity<PromoCode> getPromoCodeById(@PathVariable("id") int id) {
        Optional<PromoCode> promoCodeData = promoCodeRepository.findById(id);
        if (promoCodeData.isPresent()) {
            return new ResponseEntity<>(promoCodeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create a new promo code
    @PostMapping
    public ResponseEntity<PromoCode> createPromoCode(@RequestBody PromoCode promoCode) {
        try {
            PromoCode _promoCode = promoCodeRepository.save(promoCode);
            return new ResponseEntity<>(_promoCode, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update a promo code
    @PutMapping("/{id}")
    public ResponseEntity<PromoCode> updatePromoCode(@PathVariable("id") int id, @RequestBody PromoCode promoCode) {
        Optional<PromoCode> promoCodeData = promoCodeRepository.findById(id);
        
        if (promoCodeData.isPresent()) {
            PromoCode _promoCode = promoCodeData.get();
            _promoCode.setCode(promoCode.getCode());
            _promoCode.setDiscount(promoCode.getDiscount());
            _promoCode.setExpiryDate(promoCode.getExpiryDate());
            return new ResponseEntity<>(promoCodeRepository.save(_promoCode), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Delete a promo code
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePromoCode(@PathVariable("id") int id) {
        try {
            promoCodeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find promo code by code
    @GetMapping("/code/{code}")
    public ResponseEntity<PromoCode> findPromoCodeByCode(@PathVariable String code) {
        try {
            PromoCode promoCode = promoCodeRepository.findByCode(code);
            
            if (promoCode == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(promoCode, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Find valid promo codes (not expired)
    @GetMapping("/valid")
    public ResponseEntity<List<PromoCode>> findValidPromoCodes() {
        try {
            List<PromoCode> promoCodes = promoCodeRepository.findByExpiryDateAfter(LocalDate.now());
            
            if (promoCodes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(promoCodes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
