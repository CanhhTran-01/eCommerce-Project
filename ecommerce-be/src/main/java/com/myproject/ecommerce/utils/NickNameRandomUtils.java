package com.myproject.ecommerce.utils;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public final class NickNameRandomUtils {

    private static final String PREFIX = "user";
    private static final int RANDOM_LENGTH = 4;

    public static String generateDefaultNickName() {
        return PREFIX + "_" + randomSegment();
    }

    private static String randomSegment() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, RANDOM_LENGTH)
                .toLowerCase();
    }
}
