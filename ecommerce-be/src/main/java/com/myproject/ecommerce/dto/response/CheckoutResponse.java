package com.myproject.ecommerce.dto.response;

import com.myproject.ecommerce.enums.PaymentMethod;
import com.myproject.ecommerce.enums.ShippingMethod;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
    private String orderCode;
    private BigDecimal totalCheckout;
    private PaymentMethod paymentMethod;
    private ShippingMethod shippingMethod;
}
