package com.tung.account.service.impl;

import com.tung.account.constant.AccountType;
import com.tung.account.dto.CustomerDto;
import com.tung.account.dto.message.AccountMsgDto;
import com.tung.account.entity.Account;
import com.tung.account.entity.Customer;
import com.tung.account.exception.ResourceNotFoundException;
import com.tung.account.repository.AccountRepository;
import com.tung.account.exception.CustomerAlreadyExistedException;
import com.tung.account.repository.CustomerRepository;
import com.tung.account.service.IAccountService;
import com.tung.account.utils.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private StreamBridge streamBridge;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, StreamBridge streamBridge) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> checkedCustomer = customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());
        if (checkedCustomer.isPresent()) {
            throw new CustomerAlreadyExistedException("Customer mobile: " + customerDto.getMobileNumber() + " is already existed");
        }

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        Account savedAccount = accountRepository.save(createNewAccount(savedCustomer));

        sendCommunication(savedAccount, savedCustomer);
    }

    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        long randAccNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(randAccNumber);
        account.setCustomerId(customer.getCustomerId());
        account.setAccountType(AccountType.TYPE1.toString());

        return account;
    }

    private void sendCommunication(Account account, Customer customer) {
        AccountMsgDto accountMsgDto = new AccountMsgDto(
                account.getAccountNumber(),
                customer.getName(),
                customer.getEmail(),
                customer.getMobileNumber()
        );

        log.info("Sending Communication request for the detail: {}", accountMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountMsgDto);
        log.info("Sending Communication request successfully processed: {}", result);
    }

    @Override
    public Boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if (accountNumber != null) {
            Account account = accountRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account Number", accountNumber.toString())
            );

            account.setCommunicationSwitch(true);
            accountRepository.save(account);
            isUpdated = true;
        }

        return isUpdated;
    }
}
