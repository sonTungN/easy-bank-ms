package com.tung.account.service;

import com.tung.account.dto.CustomerDto;
import com.tung.account.entity.Account;
import com.tung.account.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IAccountService {
    List<Account> getAllAccounts();
    void createAccount(CustomerDto customerDto);
}
