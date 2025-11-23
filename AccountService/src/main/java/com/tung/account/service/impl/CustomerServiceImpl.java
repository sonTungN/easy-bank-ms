package com.tung.account.service.impl;

import com.tung.account.dto.*;
import com.tung.account.entity.Account;
import com.tung.account.entity.Customer;
import com.tung.account.exception.ResourceNotFoundException;
import com.tung.account.repository.AccountRepository;
import com.tung.account.repository.CustomerRepository;
import com.tung.account.service.ICustomerService;
import com.tung.account.service.client.CardsFeignClient;
import com.tung.account.service.client.LoansFeignClient;
import com.tung.account.utils.mapper.AccountMapper;
import com.tung.account.utils.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository, CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerDto getCustomerByMobileNumber(String mobileNumber) {
        Customer checkedCustomer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );
        Account account = accountRepository.findAccountByCustomerId(checkedCustomer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", checkedCustomer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(checkedCustomer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        return customerDto;
    }

    @Override
    @Transactional
    @Modifying
    public boolean deleteCustomerByMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
                );

        customerRepository.deleteById(customer.getCustomerId());
        accountRepository.deleteAccountByCustomerId(customer.getCustomerId());

        return true;
    }

    @Override
    public CustomerDetailsDto getCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber)
        );

        Account account = accountRepository.findAccountByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        AccountDto accountDto = AccountMapper.mapToAccountDto(account, new AccountDto());

        customerDetailsDto.setAccountDto(accountDto);

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoansDetails(mobileNumber);
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);

        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
