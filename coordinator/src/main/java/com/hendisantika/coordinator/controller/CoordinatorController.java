package com.hendisantika.coordinator.controller;

import com.hendisantika.coordinator.dto.TransactionData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/initiate_2pc")
    public String initiateTwoPhaseCommit(@RequestBody TransactionData transactionData) {
        if (callPreparePhase(transactionData)) {
            if (callCommitPhase(transactionData)) {
                return "Transavction committed successfully.";
            }

            callRollback(transactionData);
            return "Transaction Rollback";
        }

        callRollback(transactionData);
        return "Transaction Rollback";
    }

    private boolean callPreparePhase(TransactionData transactionData) {
        try {
            boolean isOrderSuccess = callServices("http://localhost:8081/prepare_order", transactionData);
            boolean isPaymentSuccess = callServices("http://localhost:8082/prepare_paymnet", transactionData);

            return isOrderSuccess && isPaymentSuccess;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean callCommitPhase(TransactionData transactionData) {
        boolean isOrderSuccess = callServices("http://localhost:8081/commit_order", transactionData);
        boolean isPaymentSuccess = callServices("http://localhost:8082/commit_paymnet", transactionData);

        return isOrderSuccess && isPaymentSuccess;
    }

    private boolean callServices(String url, TransactionData transactionData) {
        ResponseEntity<String> response = restTemplate.postForEntity(url, transactionData, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    private void callRollback(TransactionData transactionData) {
        callServiceRollbackPhase("http://localhost:8081/rollback_order", transactionData);
        callServiceRollbackPhase("http://localhost:8082/rollback_paymnet", transactionData);
    }
}
