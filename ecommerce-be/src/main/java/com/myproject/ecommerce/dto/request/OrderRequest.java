package com.myproject.ecommerce.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String shippingMethod;
    private BigDecimal shippingFee;
    private String note;
}
