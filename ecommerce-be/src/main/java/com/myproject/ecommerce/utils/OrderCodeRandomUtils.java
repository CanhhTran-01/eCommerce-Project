package com.myproject.ecommerce.utils;

import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderCodeRandomUtils {
    private static final String PREFIX = "ORD";
    private static final int RANDOM_LENGTH = 5;

    public static String generateCode() {
        return PREFIX + randomSegment();
    }

    private static String randomSegment() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, RANDOM_LENGTH)
                .toUpperCase();
    }
}
