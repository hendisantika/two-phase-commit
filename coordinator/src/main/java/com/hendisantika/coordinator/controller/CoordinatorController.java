package com.hendisantika.coordinator.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * Project : two-phase-commit
 * User: hendi
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 10/14/2023
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class CoordinatorController {

    private final RestTemplate restTemplate = new RestTemplate();
}
