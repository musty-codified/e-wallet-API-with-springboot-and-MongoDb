package com.mustycodified.ewalletAPIwithspringbootandMongoDB.enums;

public enum TransactionType {
    TRANSACTION_TYPE_WITHDRAW("withdrawal"),
    TRANSACTION_TYPE_TRANSFER("transfer"),
    TRANSACTION_TYPE_DEPOSIT("deposit");
    private final String transaction;
    TransactionType(String transaction) {
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }
}
