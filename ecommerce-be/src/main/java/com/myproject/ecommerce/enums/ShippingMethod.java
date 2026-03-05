package com.myproject.ecommerce.enums;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShippingMethod {
    STANDARD(BigDecimal.valueOf(30000.0)),
    FAST(BigDecimal.valueOf(50000.0)),
    SUPER_FAST(BigDecimal.valueOf(80000.0));

    private final BigDecimal shippingMethodPrice;
}
