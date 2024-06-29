package com.kassymova.ecommerceproduct.product;


import com.kassymova.ecommerceproduct.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var prod = mapper.toProduct(request);
        return repository.save(prod).getId();
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request){
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = repository.findAllByIdInOrderById(productIds);

        if(productIds.size() != storedProducts.size()){
            throw new ProductPurchaseException("One or more product does not exists");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var productPurchase = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < productIds.size(); i++) {
            var product = storedProducts.get(i);
            var productsRequest = sortedRequest.get(i);
            if(product.getAvailableQuantity() < productsRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productsRequest.productId());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productsRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            productPurchase.add(mapper.toProductPurchaseResponse(product, productsRequest.quantity()));
        }

        return productPurchase;
    }

}
