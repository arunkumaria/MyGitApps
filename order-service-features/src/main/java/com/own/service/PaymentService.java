package com.own.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.own.enums.PaymentStatus;

@Service
public class PaymentService {

    public PaymentStatus processPayment() {

        boolean success = new Random().nextBoolean();

        return success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
    }
}