package com.example.springdata.services;

import com.example.springdata.entities.Product;
import com.example.springdata.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    public void doSomething() {
        // \то значит что в одном методе все должно быть сделан вместе с транзакцикей
        // если ранзакция завершается то и метод тоже
    }

    public Product findById(Long id) {
//        if  (productRepository.existsById(id)){
//            return productRepository.findById(id).get();
//        }
     return    productRepository.findById(id).orElse(null);
    }

    public Product findAll() {
       return (Product) productRepository.findAll();
    }

    public Product findByTitle(String title){
        return productRepository.findByTitle(title);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getProductsWithPagingAndFiltering(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification, pageable);
    }

    public Product incrementViewAndReturnProduct(Long id) {
        Product product=findById(id);
        product.setViews(product.getViews()+1);
        productRepository.save(product);
        return product;
    }

    public void saveOrUpdate(Product product) {
        // repo will save or update
        productRepository.save(product);
    }

    public List<Product> getTop3List() {
        return productRepository.getTop3Products().stream().limit(3).collect(Collectors.toList());
    }
}
