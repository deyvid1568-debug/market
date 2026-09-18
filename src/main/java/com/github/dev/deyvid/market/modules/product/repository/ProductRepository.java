package com.github.dev.deyvid.market.modules.product.repository;

import com.github.dev.deyvid.market.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsBySku(String sku);

    Optional<Product> findBySku(String sku);
}
