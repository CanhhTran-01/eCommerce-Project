package com.myproject.ecommerce.utils;

import java.util.concurrent.ThreadLocalRandom;

public class CustomerNameRandomUtils {

    // Sinh số nguyên ngẫu nhiên trong khoảng [min, max)
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    // Sinh tên mặc định dạng "User_XXXX"
    public static String generateDefaultName() {
        int randomNumber = randomInt(1000, 10000);
        return ("User_" + randomNumber);
    }
}
