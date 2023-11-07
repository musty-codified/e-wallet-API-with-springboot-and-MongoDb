package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.AccountDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.BankDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.FundTransferDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.paystack.InitiateTransactionDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.*;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.Transaction;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.Wallet;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums.TransactionType;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.exceptions.ValidationException;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.TransactionRepository;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.WalletRepository;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.TransactionService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.WalletService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.AppUtils;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.LocalMemStorage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final RestTemplate restTemplate;
    private final AppUtils appUtil;
    private final LocalMemStorage localMemStorage;
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    private final WalletRepository walletRepository;
    @Value("${api.paystack_secret}")
    private String apiKey;

    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    //Set headers for querying Paystack's endpoints
    private HttpHeaders getHttpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + apiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    final HttpHeaders headers = getHttpHeaders();
    @Override
    public ApiResponse initiateTransaction(InitiateTransactionDto transactionDto) {
        String url = "https://api.paystack.co/transaction/initialize";
       transactionDto.setAmount(transactionDto.getAmount() + "00");

        HttpEntity <InitiateTransactionDto> entity = new HttpEntity<>(transactionDto, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, ApiResponse.class).getBody();

    }

    @Override
    public ApiResponse<TransactionInitResponseDto> verifyTransaction(String reference) {
        String url = "https://api.paystack.co/transaction/verify/ " + reference;
        HttpEntity entity = new HttpEntity(headers);

     ResponseEntity<ApiResponse> apiResponse =
              restTemplate.exchange(url, HttpMethod.GET, entity, ApiResponse.class);

     TransactionInitResponseDto responseData =
             appUtil.getMapper().convertValue(Objects.requireNonNull(apiResponse.getBody()).getData(), TransactionInitResponseDto.class);

      responseData.setAmount(responseData.getAmount().divide(new BigDecimal(100), 2, RoundingMode.DOWN));
         appUtil.print("Initiate transaction response data " + responseData);

         //Log transaction if transaction is not logged...
        if (!transactionRepository
                .existsByReferenceOrId(responseData.getReference(), responseData.getId())) {

            if (responseData.getStatus().equalsIgnoreCase("success")) { // Update wallet only when transaction is successful
                walletService.updateWallet(responseData.getCustomer().getEmail(), responseData.getAmount());
            }

            //save transaction to DB for transaction history purposes
            responseData.setTransactionType(TransactionType.TRANSACTION_TYPE_DEPOSIT.getTransaction());
            this.saveTransaction(responseData);
        }

        return new ApiResponse<>(responseData.getStatus(),
                responseData.getGateway_response().equalsIgnoreCase("success"), responseData);
    }

    @Override
    public Page<TransactionInitResponseDto> listTransactions(int page, int limit, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageableRequest = PageRequest.of(page, limit, sort);

      Page <Transaction> transactions = transactionRepository.findAll(pageableRequest);

      List<TransactionInitResponseDto> transactionList = transactions.stream()
              .map(transaction -> appUtil.getMapper().convertValue(transaction, TransactionInitResponseDto.class))
              .collect(Collectors.toList());

      //page zero is the first page by default
        if (page > 0) page = page -1;
        int max = Math.min(limit * (page + 1), transactionList.size());
        int min = page * limit;
        return new PageImpl<>(transactionList.subList(min, max), pageableRequest, transactionList.size());
    }

    //==============================================transfer==================================================//
    @Override
    public ApiResponse<List<BankDto>> fetchBanks(String currency, String type) {

        //((type == null)? "": "&type="+type)
        String url = "https://api.paystack.co/bank" + currency+((type == null)? "": "&type="+type);

        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<BankListResponseDto> apiResponse =
                restTemplate.exchange(url, HttpMethod.GET, entity, BankListResponseDto.class);
        List<BankDto> bankList = Objects.requireNonNull(apiResponse.getBody()).getData().stream()
                .map(bank -> appUtil.getMapper().convertValue(bank, BankDto.class))
                .filter(BankDto::isActive)
                .collect(Collectors.toList());
        return new ApiResponse<>(apiResponse.getBody().getMessage(), apiResponse.getBody().isStatus(), bankList);
    }

    @Override
    public ApiResponse<FundTransferDto> createTransferRecipient(AccountDto accountDto) {
        String requestUrl = "https://api.paystack.co/transferrecipient";

        HttpEntity<AccountDto> entity = new HttpEntity<>(accountDto, headers);

     ResponseEntity<ApiResponse<FundTransferDto>> apiResponse = null;
     try {
      apiResponse = restTemplate.exchange(requestUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});

     } catch (Exception e){
         e.printStackTrace();
         logger.error("error getting response from paystack");
     }
        TransferRecipientDto transferRecipientDto =
             appUtil.getMapper().convertValue(Objects.requireNonNull(apiResponse.getBody()).getData(), TransferRecipientDto.class);

        //create and save unique transfer reference in memcached for use while initiating the fund transfer
     String uniqueTransferReference = appUtil.generateSerialNumber("TRF_");
        localMemStorage.save(transferRecipientDto.getRecipient_code(),
             uniqueTransferReference, 3600);

     FundTransferDto fundTransferDto = FundTransferDto.builder()
             .recipient_code(transferRecipientDto.getRecipient_code())
             .reference(uniqueTransferReference)
             .build();
        return new ApiResponse<>( apiResponse.getBody().getMessage(), apiResponse.getBody().isStatus(), fundTransferDto);
    }

    @Override
    public Page<TransferRecipientDto> listTransferRecipient(int perPage, int page) {
        String requestUrl = "https://api.paystack.co/transferrecipient";

        HttpEntity entity = new HttpEntity<>(headers);

       ResponseEntity<TransferRecipientList>  apiResponse =
               restTemplate.exchange(requestUrl, HttpMethod.GET, entity, TransferRecipientList.class);

       List <TransferRecipientDto> transferRecipientList = Objects.requireNonNull(apiResponse.getBody()).getData().stream()
               .map(recipient -> appUtil.getMapper().convertValue(recipient, TransferRecipientDto.class))
               .filter(TransferRecipientDto::isActive)
               .collect(Collectors.toList());
        return new PageImpl<>(transferRecipientList);
    }


    @Override
    public ApiResponse<TransactionInitResponseDto> initiateTransfer(FundTransferDto fundTransferDto) {

        if (!localMemStorage.keyExist(fundTransferDto.getRecipient_code())) {
            throw new ValidationException("Transfer session has expired please try again");
        }
        BigDecimal balance = walletRepository.findByEmail(fundTransferDto.getEmail())
                .map(Wallet::getBalance).orElse(BigDecimal.ZERO);

        if(balance.compareTo(fundTransferDto.getAmount())<0)
            throw new ValidationException("Insufficient balance");

        localMemStorage.clear(fundTransferDto.getRecipient_code());

        String requestUrl = "https://api.paystack.co/transfer";

        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<ApiResponse> apiResponse =
                restTemplate.exchange(requestUrl, HttpMethod.POST, entity, ApiResponse.class);

        //map response to FundTransferDto
        TransactionInitResponseDto transactionInitResponseDto =
                appUtil.getMapper().convertValue(Objects.requireNonNull(apiResponse.getBody()).getData(), TransactionInitResponseDto.class);
        transactionInitResponseDto.setTransactionType(TransactionType.TRANSACTION_TYPE_TRANSFER.getTransaction());
        transactionInitResponseDto.setAmount(transactionInitResponseDto.getAmount().divide(new BigDecimal(100), 2, RoundingMode.DOWN));

        return new ApiResponse<>(apiResponse.getBody().getMessage(), apiResponse.getBody().isStatus(), this.saveTransaction(transactionInitResponseDto));
    }

    @Override
    public TransactionInitResponseDto saveTransaction(TransactionInitResponseDto transactionDto) {

        if (!transactionRepository
                .existsByReferenceOrId(transactionDto.getReference(), transactionDto.getId())) {

            Transaction transaction = appUtil.getMapper().convertValue(transactionDto, Transaction.class);
            transaction.setEmail(transactionDto.getCustomer().getEmail());

            try{
                transactionRepository.save(transaction);
            } catch (Exception e){
                e.printStackTrace();
                logger.error("error logging transaction {"+e.getMessage()+"} ");
            }
        }

        return transactionDto;
    }

    @Override
    public ApiResponse<AccountDto> resolveBankDetails(String accountNumber, String bankCode) {
        String requestUrl = "https://api.paystack.co/bank/resolve?account_number="+accountNumber +"&bank_code="+bankCode;
        HttpEntity entity = new HttpEntity<>(headers);

        //Retrieve account details from Paystack 'bank resolve' API
        ResponseEntity<ApiResponse> apiResponse= null;
        try{
            apiResponse = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, ApiResponse.class);

        } catch (Exception e){
            e.printStackTrace();
            logger.error("error getting paytack response");
        }

        //map response data to AccountDto
     AccountDto accountDto = appUtil.getMapper()
             .convertValue(Objects.requireNonNull(apiResponse.getBody()).getData(), AccountDto.class);
        appUtil.print("response data: " + apiResponse );

        return new ApiResponse<>(apiResponse.getBody().getMessage(), apiResponse.getBody().isStatus(), accountDto);
    }


}
