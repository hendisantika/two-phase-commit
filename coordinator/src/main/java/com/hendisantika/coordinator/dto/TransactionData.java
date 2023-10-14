package com.hendisantika.coordinator.dto;

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
 * Time: 7:43 AM
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class TransactionData {
    private String orderNumber;
    private String item;
    private String price;
    private String paymentMode;
}
