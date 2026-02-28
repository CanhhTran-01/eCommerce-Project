package com.myproject.ecommerce.dto.request;

import java.math.BigDecimal;
import lombok.*;

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
