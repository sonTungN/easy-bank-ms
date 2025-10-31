package com.tung.account.utils.mapper;

import com.tung.account.dto.AccountDto;
import com.tung.account.dto.CustomerDto;
import com.tung.account.entity.Account;
import com.tung.account.entity.Customer;

public class CustomerMapper {
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());

        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());

        return customer;
    }
}
