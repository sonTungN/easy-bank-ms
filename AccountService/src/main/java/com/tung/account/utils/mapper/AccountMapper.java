package com.tung.account.utils.mapper;

import com.tung.account.dto.AccountDto;
import com.tung.account.entity.Account;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account, AccountDto accountDto) {
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        return accountDto;
    }

    public static Account mapToAccount(AccountDto accountDto, Account account) {
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());

        return account;
    }
}
