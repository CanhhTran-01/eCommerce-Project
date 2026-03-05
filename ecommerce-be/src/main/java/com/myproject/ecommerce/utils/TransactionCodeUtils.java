package com.myproject.ecommerce.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionCodeUtils {
    public static String generate() {
        return "TXN-" + System.currentTimeMillis();
    }
}
