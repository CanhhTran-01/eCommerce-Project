package com.myproject.ecommerce.dto.response;

import com.myproject.ecommerce.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderCode;
    private OrderStatus status;
    private String paymentStatus;
    private String shippingMethod;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private BigDecimal finalAmount;
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String note;
    private LocalDateTime updatedAt;
}
