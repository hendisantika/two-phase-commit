package com.hendisantika.payment.controller;

import com.hendisantika.payment.dto.TransactionData;
import com.hendisantika.payment.entity.Payment;
import com.hendisantika.payment.entity.PaymentStatus;
import com.hendisantika.payment.repositopry.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : two-phase-commit
 * User: hendi
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 10/14/2023
 * Time: 7:37 AM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentRepository paymentRepository;

    @PostMapping("/prepare_payment")
    public ResponseEntity<String> prepareOrder(@RequestBody TransactionData transactionData) {
        try {
            Payment payment = new Payment();
            payment.setOrderNumber(transactionData.getOrderNumber());
            payment.setItem(transactionData.getItem());
            payment.setPreparationStatus(PaymentStatus.PENDING.name());
            payment.setPrice(transactionData.getPrice());
            payment.setPaymentMode(transactionData.getPaymentMode());
            paymentRepository.save(payment);

            if (shouldFailedDuringPrepare()) {
                throw new RuntimeException("Prepare phase failed for payment " + transactionData.getOrderNumber());
            }

            return ResponseEntity.ok("Payment prepared successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during payment preparation");
        }
    }

    private boolean shouldFailedDuringPrepare() {
        return false;
    }
}
