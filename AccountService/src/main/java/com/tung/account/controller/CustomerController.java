package com.tung.account.controller;

import com.tung.account.dto.CustomerDto;
import com.tung.account.dto.common.ResponseDto;
import com.tung.account.entity.Customer;
import com.tung.account.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/s1")
public class CustomerController {
    private ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getAllCustomers());
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<CustomerDto> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerByEmail(email));
    }

    @DeleteMapping("/customer/{email}")
    public ResponseEntity<ResponseDto> deleteCustomerByEmail(@PathVariable String email) {
        boolean isDeleted = customerService.deleteCustomerByEmail(email);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("200", "Delete customer successfully!"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto("500", "Failed to delete customer data"));
        }


    }
}
