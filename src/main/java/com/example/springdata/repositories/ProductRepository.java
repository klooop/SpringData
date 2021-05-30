package com.example.springdata.repositories;

import com.example.springdata.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    Product findByTitle(String title);

    @Query("SELECT p FROM Product p ORDER BY p.views desc")
    public List<Product> getTop3Products();
}
