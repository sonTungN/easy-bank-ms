package com.tung.account.function;

import com.tung.account.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountFunction {

    private static final Logger log = LoggerFactory.getLogger(AccountFunction.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountService accountService) {
        return accountNumber -> {
            log.info("Update Communication status for the account number: {}", accountNumber.toString());
            accountService.updateCommunicationStatus(accountNumber);
        };
    }
}
