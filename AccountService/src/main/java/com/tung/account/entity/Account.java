package com.tung.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account extends BaseEntity {
    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    private Long customerId;

    private String accountType;
}
