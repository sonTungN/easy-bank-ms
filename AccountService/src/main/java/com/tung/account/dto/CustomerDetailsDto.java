package com.tung.account.dto;

import lombok.Data;

@Data
public class CustomerDetailsDto {
    private String name;
    private String email;
    private String mobileNumber;
    private AccountDto accountDto;
    private CardsDto cardsDto;
    private LoansDto loansDto;
}
