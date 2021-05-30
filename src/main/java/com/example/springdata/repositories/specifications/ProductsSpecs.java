package com.example.springdata.repositories.specifications;

import com.example.springdata.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductsSpecs {
    public static Specification<Product> titleContains(String word) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + word + "%");
    }
    public static Specification<Product> priceGreaterThanOrEq(BigDecimal value){
        return  (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
    }
    public static Specification<Product> priceLesserThanOrEq(BigDecimal value){
        return  (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
    }
}
