package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers;


import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")

@Tag(name = "Payment Endpoint", description = "<h3>To deposit: </h3> " +
        "<ol>" +
        "<li>Go to '/deposit/initiate' endpoint and enter your details. The <b>callbackUrl</b> and <b>metadata</b> fields are optional. " +
        "just leave them be</li> " +
        "<li>Copy the payment link returned in the response object to any browser to make your payment.</li>"

)
public class PaymentController {

    private final TransactionService transactionService;

    @PostMapping("/deposit/initiate")
    public ResponseEntity<ApiResponse> getPaymentUrl(@RequestBody InitiateTransactionDto transactionDto){
        return ResponseEntity.ok(transactionService.initiateTransaction(transactionDto));
    }
}
