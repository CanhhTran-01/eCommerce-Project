package com.myproject.ecommerce.utils;

import java.math.BigDecimal;

public class CurrentProductPriceUtils {

    // having discount unitPrice or not ?
    public static BigDecimal getCurrentPrice(BigDecimal price, BigDecimal discountPrice) {
        if (discountPrice != null) {
            return discountPrice;
        }
        return price;
    }
}
