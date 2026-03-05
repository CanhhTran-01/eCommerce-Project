package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.CheckoutRequest;
import com.myproject.ecommerce.dto.response.CheckoutResponse;
import com.myproject.ecommerce.entity.Order;
import com.myproject.ecommerce.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckoutService {
    private final OrderService orderService;
    private final PaymentService paymentService;

    // handle COD checkout
    public CheckoutResponse handleCODCheckout(Long accountId, CheckoutRequest checkoutRequest) {

        Order order = orderService.createOrder(accountId, checkoutRequest); // create order

        Payment payment = paymentService.createPayment(order, checkoutRequest); // create payment by order

        // return checkout data
        return CheckoutResponse.builder()
                .orderCode(order.getOrderCode())
                .totalCheckout(order.getFinalAmount())
                .paymentMethod(payment.getPaymentMethod())
                .shippingMethod(order.getShippingMethod())
                .build();
    }
    // ONLINE checkout ( later implements)
}
