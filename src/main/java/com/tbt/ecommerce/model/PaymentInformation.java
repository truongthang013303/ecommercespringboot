package com.tbt.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentInformation {

    @Column(name = "cardholder_name")
    private String cardholderName;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "expiration_date")
    private String expirationDate;
    @Column(name = "cvv")
    private String cvv;
}
