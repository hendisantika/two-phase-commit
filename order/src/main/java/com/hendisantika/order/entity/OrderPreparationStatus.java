package com.hendisantika.order.entity;

/**
 * Created by IntelliJ IDEA.
 * Project : two-phase-commit
 * User: hendi
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 10/14/2023
 * Time: 7:24 AM
 * To change this template use File | Settings | File Templates.
 */
public enum OrderPreparationStatus {
    NOT_PREPARED,
    PREPARING,
    COMMITTED,
    ROLLBACK
}
