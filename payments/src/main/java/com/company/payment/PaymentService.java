package com.company.payment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public String create(ReqCreatePayment req) {
        Payment payment = Payment.builder()
                .amount(req.getAmount())
                .description(req.getDescription())
                .toCard(req.getToCard())
                .fromCard(req.getFromCard())
                .time(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);
        return "Payment Created Successfully";
    }
}
