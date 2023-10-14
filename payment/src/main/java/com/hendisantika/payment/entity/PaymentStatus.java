package com.hendisantika.payment.entity;

/**
 * Created by IntelliJ IDEA.
 * Project : two-phase-commit
 * User: hendi
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 10/14/2023
 * Time: 7:35 AM
 * To change this template use File | Settings | File Templates.
 */
public enum PaymentStatus {
    PENDING,
    APPROVED,
    DECLINED,
    CANCELLED,
    ROLLBACK
}
