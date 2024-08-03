package com.company.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqCreatePayment {
    private Double amount;
    private String fromCard;
    private String toCard;
    private String description;
}
