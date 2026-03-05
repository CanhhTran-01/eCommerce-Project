package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.PaymentMethod;
import com.myproject.ecommerce.enums.ShippingMethod;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private String receiverName;
    private String receiverPhone;
    private String shippingAddress;

    List<OrderItemRequest> itemRequestList = new ArrayList<>();

    private String note;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
}
