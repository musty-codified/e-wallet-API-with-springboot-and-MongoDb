package com.mustycodified.ewalletAPIwithspringbootandMongoDB.controllers.consumers;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.AccountDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.BankDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.FundTransferDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.ApiResponse;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransactionInitResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.TransferRecipientDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")

@Tag(name = "Payment Endpoints", description = "<h3>Initiating Payments using the paystack's payment gateway: </h3> " +
        "<ol>" +
        "<li>Go to 'initiate/deposit' endpoint and enter your details. The <b>callbackUrl</b> and <b>metadata</b> fields are optional. " +
        "<li>Copy the redirection payment link returned in the response object to any browser to navigate to paystack's payment page and make your payment.</li>" +
        "<li>Copy the reference code returned if payment was successful and go to 'verify/{payment_reference}' endpoint to verify.</li>" +
        "</ol> " +
        "<h3> For withdrawal which is implemented as transfer:</h3> " +
        "<ol> " +
        "<li>Go to 'withdrawal/create-transfer-recipient' endpoint to create your Transfer recipient.</li> " +
        "<p>Use the <b>'validate-account-details'</b> endpoint to validate your account details and the " +
        "<b>'banks'</b> endpoint to see all available banks.</p>" +
        "<li>Copy the <b>reference</b> code 'TRF_....' and the <b>recipient</b> code 'RCP_....' generated and navigate to 'withdrawal/send-money'.</li> " +
        "<li>After initiating your withdrawal, you will receive an error code response because the payment was a test payment.</li>" +
        "</ol> "
)
public class PaymentController {

    private final TransactionService transactionService;

    @Operation(summary = "Initiates a transaction to get payment link")
    @PostMapping("/initiate/deposit")
    public ResponseEntity<ApiResponse<String>> getPaymentUrl(@RequestBody InitiateTransactionDto transactionDto){
        return ResponseEntity.ok(transactionService.initiateTransaction(transactionDto));
    }

    @Operation(summary = "Paystack processes the payment and sends a callback to this webhook URL.")
    @PostMapping("/status-webhook")
    public ResponseEntity<String> getPaymentStatus(@RequestBody InitiateTransactionDto transactionResponseDto) {

        return ResponseEntity.ok().body("Thank you");
    }
    @Operation(summary = "Verify the callback by checking the payment status with Paystack's API."
            , description = "Updates the user's wallet balance with the payment amount if the payment is successful")
    @GetMapping ("/verify/{payment_reference}")
    public ResponseEntity<ApiResponse<TransactionInitResponseDto>> confirmPayment(
            @Parameter(description = "Use the reference number generated when the transaction was initiated")
            @PathVariable String payment_reference ){
        return ResponseEntity.ok(transactionService.verifyTransaction(payment_reference));
    }

    @Operation(summary = "list all transactions carried out on the integration")
    @GetMapping("/wallet/transactions")
    public ResponseEntity<Page<TransactionInitResponseDto>> listTransactions(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(value = "limit", defaultValue = "5") int limit,
                                                                             @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                                             @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return ResponseEntity.ok(transactionService.listTransactions(page, limit, sortBy, sortDir));

    }


    //===============================================Transfer transactions===========================================//
    @Operation(summary = "Fetches list of supported banks that are active in a country")
    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<List<BankDto>>> fetchBanks(
            @Parameter(description = "NGN for Nigeria, GHS for Ghana etc") @RequestParam (name = "currency", required = false) String currency,
            @Parameter(description = "(Optional) enter nuban or mobile money etc") @RequestParam(name = "type", required = false) String type){
        return ResponseEntity.ok(transactionService.fetchBanks(currency, type));
    }

    @Operation(summary = "Verify recipient's account before making transfers")
    @GetMapping("/validate-account-details")
    public ResponseEntity<ApiResponse<AccountDto>> verifyAccountDetails(
            @Parameter(description = "10 digits account number") @RequestParam (name = "account_number") String accountNumber,
            @Parameter(description = "057 for zenith bank, 044 - access bank, etc. Read the docs") @RequestParam (name = "bank_code") String bankCode){
        return ResponseEntity.ok(transactionService.resolveBankDetails(accountNumber, bankCode));
    }

    @Operation(summary = "Before sending money to an account you need to create a recipient with account details")
    @PostMapping("/withdrawal/create-transfer-recipient")
    public ResponseEntity<ApiResponse<FundTransferDto>> createTransferRecipient(@RequestBody AccountDto accountDto){
        return ResponseEntity.ok(transactionService.createTransferRecipient(accountDto));
    }
    @Operation(summary = "Send money to another account" )
    @PostMapping("withdrawal/send-money")
    public ResponseEntity<ApiResponse<TransactionInitResponseDto>> initiateTransfer(@RequestBody FundTransferDto fundTransferDto){
        return ResponseEntity.ok(transactionService.initiateTransfer(fundTransferDto));
    }

    @Operation(summary = "Fetch list of all created transfer recipients" )
    @GetMapping("withdrawal/list-transfer-recipients")
    public ResponseEntity<Page<TransferRecipientDto>> listTransferRecipients(@RequestParam(value = "perPage", defaultValue = "50") int perPage,
                                                                             @RequestParam(value = "page", defaultValue = "1") int page){
        return ResponseEntity.ok(transactionService.listTransferRecipient(perPage, page));
    }
}
