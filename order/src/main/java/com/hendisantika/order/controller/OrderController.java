package com.hendisantika.order.controller;

import com.hendisantika.order.dto.TransactionData;
import com.hendisantika.order.entity.Order;
import com.hendisantika.order.entity.OrderPreparationStatus;
import com.hendisantika.order.repository.OrderRepository;
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
 * Time: 7:26 AM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;

    @PostMapping("/prepare_order")
    public ResponseEntity<String> prepareOrder(@RequestBody TransactionData transactionData) {
        try {
            Order order = new Order();
            order.setOrderNumber(transactionData.getOrderNumber());
            order.setItem(transactionData.getItem());
            order.setPreparationStatus(OrderPreparationStatus.PREPARING.name());
            orderRepository.save(order);

            if (shouldFailedDuringPrepare()) {
                throw new RuntimeException("Prepare phase failed for order " + transactionData.getOrderNumber());
            }

            return ResponseEntity.ok("Order prepared successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during oredr preparation");
        }
    }
}
