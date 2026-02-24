package com.myproject.ecommerce.utils;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class UserCodeRandomUtils {

    private static final String PREFIX = "USR";
    private static final int RANDOM_LENGTH = 4;

    public static String generateUserCode() {
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
