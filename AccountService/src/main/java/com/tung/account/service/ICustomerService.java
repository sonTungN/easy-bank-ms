package com.tung.account.service;

import com.tung.account.dto.CustomerDto;
import com.tung.account.entity.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> getAllCustomers();
    CustomerDto getCustomerByEmail(String email);
    boolean deleteCustomerByEmail(String email);
}
