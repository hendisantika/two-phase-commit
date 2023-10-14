package com.hendisantika.order.repository;

import com.hendisantika.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : two-phase-commit
 * User: hendi
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 10/14/2023
 * Time: 7:25 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByItem(String item);
}
