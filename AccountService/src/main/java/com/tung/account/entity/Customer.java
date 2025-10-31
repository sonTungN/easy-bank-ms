package com.tung.account.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;

    private String email;
}
