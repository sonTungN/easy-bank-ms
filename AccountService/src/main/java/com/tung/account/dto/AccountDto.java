package com.tung.account.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDto {
    private Long accountNumber;
    private String accountType;
}
