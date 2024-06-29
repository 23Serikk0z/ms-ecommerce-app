package com.kassymova.ecommerce.payment;

import com.kassymova.ecommerce.customer.CustomerResponse;
import com.kassymova.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
