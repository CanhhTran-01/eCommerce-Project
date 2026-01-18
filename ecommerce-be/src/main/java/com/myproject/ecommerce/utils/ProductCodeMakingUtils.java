package com.myproject.ecommerce.utils;

import java.time.Year;
import java.util.concurrent.ThreadLocalRandom;

public class ProductCodeMakingUtils {

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static String generateProductCode() {
        int yearNow = Year.now().getValue();
        int randomNumber = randomInt(1000, 10000);
        return ("PRD-" + yearNow + "-" + randomNumber);
    }
}
