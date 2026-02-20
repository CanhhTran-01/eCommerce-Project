package com.myproject.ecommerce.utils;

import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.UUID;

@NoArgsConstructor
public final class ProductCodeMakingUtils {

    private static final String PREFIX = "PRD";
    private static final int RANDOM_LENGTH = 4;

    public static String generateProductCode() {
        int year = Year.now().getValue();
        String random = randomSegment();
        return PREFIX + "-" + year + "-" + random;
    }

    private static String randomSegment() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, RANDOM_LENGTH)
                .toUpperCase();
    }
}