package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers;


import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransactionInitResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")

@Tag(name = "Payment Endpoint", description = "<h3>To deposit: </h3> " +
        "<ol>" +
        "<li>Go to '/deposit/initiate' endpoint and enter your details. The <b>callbackUrl</b> and <b>metadata</b> fields are optional. " +
        "just leave them be</li> " +
        "<li>Copy the payment link returned in the response object to any browser to make your payment.</li>" +
        "<li>Copy the reference code returned if it was successful and go to '/verify/{payment_reference}' endpoint to verify if your deposit was successful</li>" +
        "</ol> "

)
public class PaymentController {

    private final TransactionService transactionService;

    @Operation(summary = "Initiates a transaction to get payment link")
    @PostMapping("/deposit/initiate")
    public ResponseEntity<ApiResponse> getPaymentUrl(@RequestBody InitiateTransactionDto transactionDto){
        return ResponseEntity.ok(transactionService.initiateTransaction(transactionDto));
    }
    @Operation(summary = "Verifies the success or failure of a transaction")
    @GetMapping ("/verify/{payment_reference}")
    public ResponseEntity<ApiResponse<TransactionInitResponseDto>> confirmPayment(
            @Parameter(description = "Use the reference number generated when the transaction was initiated")
            @PathVariable String payment_reference ){
        return ResponseEntity.ok(transactionService.verifyTransaction(payment_reference));
    }
}
