package com.kassymova.ecommerceproduct.product;


import com.kassymova.ecommerceproduct.category.Category;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {


    public Product toProduct(ProductRequest req){
        return Product.builder()
                .id(req.id())
                .name(req.name())
                .description(req.description())
                .availableQuantity(req.availableQuantity())
                .price(req.price())
                .category(Category.builder()
                        .id(req.categoryId())
                        .build())
                .build();
    }

    public ProductResponse toProductResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity){
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
