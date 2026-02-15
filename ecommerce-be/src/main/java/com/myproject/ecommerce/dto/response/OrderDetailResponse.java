package com.myproject.ecommerce.dto.response;

import com.myproject.ecommerce.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private String orderCode;
    private LocalDateTime updatedAt;
    private OrderStatus status;

    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;
    private String note;

    private List<OrderItemResponse> orderItemResponseList = new ArrayList<>();

    private String shippingMethod;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private BigDecimal finalAmount;
}
