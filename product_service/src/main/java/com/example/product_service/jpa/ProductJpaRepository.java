package com.example.product_service.jpa;

import com.example.product_service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("from Product p where p.productIdentifier in :productIdentifiers")
    Collection<Product> findProductsByProductIdentifier(Iterable<UUID> productIdentifiers);
}
