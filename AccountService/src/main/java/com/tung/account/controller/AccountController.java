package com.tung.account.controller;

import com.tung.account.dto.CustomerDto;
import com.tung.account.dto.common.ResponseDto;
import com.tung.account.entity.Account;
import com.tung.account.entity.Customer;
import com.tung.account.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/s1")
public class AccountController {
    private IAccountService accountService;

    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.getAllAccounts());
    }

    @PostMapping("/account/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Create new Account successfully!"));
    }

}
