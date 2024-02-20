package com.example.API.repositories;

import com.example.API.models.ProductsModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepositories extends JpaRepository<ProductsModels, UUID> {
    Optional<ProductsModels> findBySku(String sku);
}
