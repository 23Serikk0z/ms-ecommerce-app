package com.kassymova.ecommerce.kafka;

import com.kassymova.ecommerce.customer.CustomerResponse;
import com.kassymova.ecommerce.order.PaymentMethod;
import com.kassymova.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) { }
