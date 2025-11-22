package com.tung.account.controller;

import com.tung.account.dto.AccountContactInfoDto;
import com.tung.account.dto.CustomerDto;
import com.tung.account.dto.common.ResponseDto;
import com.tung.account.entity.Account;
import com.tung.account.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AccountController {
    private IAccountService accountService;
    private AccountContactInfoDto accountContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    public AccountController(IAccountService accountService, AccountContactInfoDto accountContactInfoDto) {
        this.accountService = accountService;
        this.accountContactInfoDto = accountContactInfoDto;
    }

    @GetMapping("/build-version")
    public ResponseEntity<String> getBuildVersion () {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
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
