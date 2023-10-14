package com.hendisantika.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * Project : two-phase-commit
 * User: hendi
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 10/14/2023
 * Time: 7:34 AM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private String item;

    private String preparationStatus;

    private String paymentMode;

    private String price;
}
