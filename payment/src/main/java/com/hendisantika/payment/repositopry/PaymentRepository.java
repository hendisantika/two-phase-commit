package com.hendisantika.payment.repositopry;

import com.hendisantika.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : two-phase-commit
 * User: hendi
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 10/14/2023
 * Time: 7:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByItem(String item);
}
