package com.tung.account.repository;

import com.tung.account.entity.Account;
import com.tung.account.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByCustomerId(Long customerId);
    void deleteAccountByCustomerId(Long customerId);
}
