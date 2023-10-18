package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl;


import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.WalletResponseDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities.Wallet;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.repositories.WalletRepository;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.WalletService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final AppUtils appUtil;
    @Override
    public WalletResponseDto updateWallet(String email, BigDecimal amount) {

   Wallet wallet = walletRepository.findByEmail(email)
           .orElse(Wallet.builder()
                    .walletUUID(appUtil.generateSerialNumber("0"))
                    .balance(BigDecimal.ZERO)
                    .email(email)
            .build());

    wallet.setBalance(wallet.getBalance().add(amount));
    wallet.setUpdatedAt();
        return appUtil.getMapper().convertValue(walletRepository.save(wallet), WalletResponseDto.class);
    }
}
