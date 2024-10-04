package com.motivank.accounts.entity;

import com.motivank.accounts.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Builder
    private Customer(String name, String email, String mobileNumber, String createdBy) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.setCreatedBy(createdBy);
        this.setCreatedAt(LocalDateTime.now());
    }

    public CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getName(),
                customer.getEmail(),
                customer.getMobileNumber()
        );
    }

}
