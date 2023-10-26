package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers;


import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.AccountDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.BankDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.FundTransferDto;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")

@Tag(name = "Payment Endpoint", description = "<h3>To deposit: </h3> " +
        "<ol>" +
        "<li>Go to '/deposit/initiate' endpoint and enter your details. The <b>callbackUrl</b> and <b>metadata</b> fields are optional. " +
        "just leave them be</li> " +
        "<li>Copy the payment link returned in the response object to any browser to make your payment.</li>" +
        "<li>Copy the reference code returned if it was successful and go to '/verify/{payment_reference}' endpoint to verify if your deposit was successful.</li>" +
        "</ol> " +
        "<h3> For withdrawal which is implemented as transfer:</h3> " +
        "<ol> " +
        "<li>Go to 'withdrawal/create-transfer-recipient' endpoint to create your Transfer recipient.</li> " +
        "<p>You can use the <b>validate-account-details</b> endpoint to validate your account details and the " +
        "<b>banks</b> endpoint to see all available banks.</p>" +
        "<li>Copy the <b>reference</b> code 'TRF_....' and the <b>recipient</b> code 'RCP_....' generated and go to 'withdrawal/send-money' endpoint.</li> " +
        "<li>After initiating your withdrawal, you will receive an error code response because the payment was a test payment.</li>" +
        "</ol> "


)
public class PaymentController {

    private final TransactionService transactionService;

    @Operation(summary = "Initiates a transaction to get payment link")
    @PostMapping("/deposit/initiate")
    public ResponseEntity<ApiResponse<String>> getPaymentUrl(@RequestBody InitiateTransactionDto transactionDto){
        return ResponseEntity.ok(transactionService.initiateTransaction(transactionDto));
    }
    @Operation(summary = "Verifies the success or failure of a transaction")
    @GetMapping ("/verify/{payment_reference}")
    public ResponseEntity<ApiResponse<TransactionInitResponseDto>> confirmPayment(
            @Parameter(description = "Use the reference number generated when the transaction was initiated")
            @PathVariable String payment_reference ){
        return ResponseEntity.ok(transactionService.verifyTransaction(payment_reference));
    }

    //   ========================================= Transfer transactions ==========================================  //
    @Operation(summary = "Fetches list of supported banks that are active in a country")
    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<List<BankDto>>> fetchBanks(
            @Parameter(description = "NGN for Nigeria, GHS for Ghana etc") @RequestParam (name = "currency") String currency,
            @Parameter(description = "(Optional) enter nuban or mobile money etc") @RequestParam(name = "type") String type){
        return ResponseEntity.ok(transactionService.fetchBanks(currency, type));
    }

    @Operation(summary = "Confirm the authenticity of recipient's account before making transfers")
    @GetMapping("/validate-account-details")
    public ResponseEntity<ApiResponse<AccountDto>> verifyAccountDetails(
            @Parameter(description = "10 digits account number") @RequestParam (name = "account_number") String accountNumber,
            @Parameter(description = "057 for zenith bank, 044 - access bank, etc. Read the docs") @RequestParam (name = "bank_code") String bankCode){
        return ResponseEntity.ok(transactionService.resolveBankDetails(accountNumber, bankCode));
    }

    @Operation(summary = "Before sending money to an account you need to create a transfer recipient with the beneficiary's account details")
    @PostMapping("/withdrawal/create-transfer-recipient")
    public ResponseEntity<ApiResponse<FundTransferDto>> createTransferRecipient(@RequestBody AccountDto accountDto){
        return ResponseEntity.ok(transactionService.createTransferRecipient(accountDto));
    }
    @Operation(summary = "Send money to an account" )
    @PostMapping("withdrawal/send-money")
    public ResponseEntity<ApiResponse<TransactionInitResponseDto>> initiateTransfer(@RequestBody FundTransferDto fundTransferDto){
        return ResponseEntity.ok(transactionService.initiateTransfer(fundTransferDto));
    }
}
