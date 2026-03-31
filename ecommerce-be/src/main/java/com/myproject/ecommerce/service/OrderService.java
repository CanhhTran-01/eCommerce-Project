package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.CheckoutRequest;
import com.myproject.ecommerce.dto.request.OrderItemRequest;
import com.myproject.ecommerce.dto.response.OrderDetailResponse;
import com.myproject.ecommerce.dto.response.OrderItemResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.Order;
import com.myproject.ecommerce.entity.OrderItem;
import com.myproject.ecommerce.entity.Product;
import com.myproject.ecommerce.enums.OrderStatus;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.mapper.OrderMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.OrderItemRepository;
import com.myproject.ecommerce.repository.OrderRepository;
import com.myproject.ecommerce.repository.ProductRepository;
import com.myproject.ecommerce.utils.CurrentProductPriceUtils;
import com.myproject.ecommerce.utils.OrderCodeRandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    // get my order detail
    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetail(Long accountId, Long orderId) {

        if (!accountRepository.existsById(accountId)) {
            throw new BaseException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        Order order = orderRepository
                .findOrderByIdAndAccountId(accountId, orderId)
                .orElseThrow(() -> new BaseException(ErrorCode.ORDER_NOT_FOUND));

        OrderDetailResponse response = orderMapper.toDetailResponse(order);

        List<OrderItemResponse> orderItemResponses =
                orderItemRepository.getOrderItemsforOrderDetailDisplay(order.getId());

        response.setOrderItemResponseList(orderItemResponses);

        return response;
    }

    // create order
    public Order createOrder(Long accountId, CheckoutRequest checkoutRequest) {
        Account account =
                accountRepository.findById(accountId).orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        // handle order
        Order order = Order.builder()
                .orderCode(OrderCodeRandomUtils.generateCode())
                .status(OrderStatus.PENDING)
                .shippingMethod(checkoutRequest.getShippingMethod())
                .shippingFee(checkoutRequest.getShippingMethod().getShippingMethodPrice())
                .receiverName(checkoutRequest.getReceiverName())
                .receiverPhone(checkoutRequest.getReceiverPhone())
                .shippingAddress(checkoutRequest.getShippingAddress())
                .note(checkoutRequest.getNote())
                .user(account.getUser())
                .build();

        // batch fetch all product - 1 query
        List<Long> productIds = checkoutRequest.getItemRequestList().stream()
                .map(OrderItemRequest::getProductId)
                .toList();

        Map<Long, Product> productMap = productRepository.findAllByIdWithLock(productIds).stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // handle order items
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.valueOf(0);

        for (OrderItemRequest itemRequest : checkoutRequest.getItemRequestList()) {

            // get product to create order itemRequest
            Product product = productMap.get(itemRequest.getProductId());
            if (product == null) {
                throw new BaseException(ErrorCode.PRODUCT_NOT_FOUND);
            }

            OrderItem orderItem = OrderItem.builder()
                    .productName(product.getProductName())
                    .quantity(itemRequest.getQuantity())
                    .imageUrl(product.getMainImageUrl())
                    .unitPrice(CurrentProductPriceUtils.getCurrentPrice(product.getPrice(), product.getDiscountPrice()))
                    .totalPrice(CurrentProductPriceUtils.getCurrentPrice(
                                    product.getPrice(), product.getDiscountPrice()) // get unit price
                            .multiply(BigDecimal.valueOf(itemRequest.getQuantity()))) // total = unit price x quantity
                    .build();
            orderItems.add(orderItem); // insert into list

            decreaseStock(product, itemRequest.getQuantity()); // reduce stock quantity of this product

            totalAmount = totalAmount.add(orderItem.getTotalPrice()); // total amount for order

            orderItem.setOrder(order); // owning side need to set for creating FK
            orderItem.setProduct(product); // owning side need to set for creating FK
        }

        // set remaining field in order entity
        order.setTotalAmount(totalAmount);
        order.setFinalAmount(totalAmount.add(checkoutRequest.getShippingMethod().getShippingMethodPrice()));
        order.setOrderItemList(orderItems);

        // save order
        return orderRepository.save(order);
    }

    public void decreaseStock(Product product, Integer itemQuantity) {
        if (product.getStockQuantity() == null || product.getStockQuantity() <= 0) {
            throw new BaseException(ErrorCode.OUT_OF_STOCK);
        }
        product.setStockQuantity(product.getStockQuantity() - itemQuantity);
    }
}
