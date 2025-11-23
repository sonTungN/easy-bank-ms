package com.tung.account.service.client;

import com.tung.account.dto.CardsDto;
import com.tung.account.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loanservice")
public interface LoansFeignClient {

    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoansDetails(@RequestParam String mobileNumber);
}
