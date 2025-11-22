package com.tung.account.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private String name;
    private String email;
    private String mobileNumber;
    private AccountDto accountDto;
}
