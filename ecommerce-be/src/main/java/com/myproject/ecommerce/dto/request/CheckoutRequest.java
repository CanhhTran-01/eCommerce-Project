package com.myproject.ecommerce.dto.request;

import com.myproject.ecommerce.enums.PaymentMethod;
import com.myproject.ecommerce.enums.ShippingMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {

    @NotBlank
    private String receiverName;

    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "PHONE_NUMBER_INVALID")
    private String receiverPhone;

    @NotBlank
    private String shippingAddress;

    @NotBlank
    List<OrderItemRequest> itemRequestList = new ArrayList<>();

    private String note;

    @NotBlank
    private ShippingMethod shippingMethod;

    @NotBlank
    private PaymentMethod paymentMethod;
}
