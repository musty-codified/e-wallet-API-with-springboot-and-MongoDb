package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos.WalletResponseDto;

import java.math.BigDecimal;

public interface WalletService {
    WalletResponseDto updateWallet(String email, BigDecimal amount);
}
