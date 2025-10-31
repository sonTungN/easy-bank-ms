package com.tung.account.service.impl;

import com.tung.account.constant.AccountType;
import com.tung.account.dto.CustomerDto;
import com.tung.account.entity.Account;
import com.tung.account.entity.Customer;
import com.tung.account.repository.AccountRepository;
import com.tung.account.exception.CustomerAlreadyExistedException;
import com.tung.account.repository.CustomerRepository;
import com.tung.account.service.IAccountService;
import com.tung.account.utils.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> checkedCustomer = customerRepository.findCustomerByEmail(customerDto.getEmail());
        if (checkedCustomer.isPresent()) {
            throw new CustomerAlreadyExistedException("Customer email: " + customerDto.getEmail() + " is already existed");
        }

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        long randAccNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(randAccNumber);
        account.setCustomerId(customer.getCustomerId());
        account.setAccountType(AccountType.TYPE1.toString());

        return account;
    }
}
