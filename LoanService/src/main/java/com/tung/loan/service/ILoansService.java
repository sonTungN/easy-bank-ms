package com.tung.loan.service;

import com.tung.loan.dto.LoansDto;

public interface ILoansService {

    boolean deleteLoan(String mobileNumber);

    void createLoan(String mobileNumber);

    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);
}
