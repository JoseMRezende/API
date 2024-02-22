package com.example.API.controllers;

import com.example.API.dtos.ProductsDto;
import com.example.API.models.ProductsModels;
import com.example.API.repositories.ProductsRepositories;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.example.API.service.ProductService.copyNonNullProperties;

@Controller
@RequestMapping(value = "/products", produces = {"application/json"})
@Tag(name = "API")
public class ProductsControllers {

    @Autowired
    ProductsRepositories productsRepositories;

    @Operation(summary = "Pesquisar produtos existentes no banco de dados", method = "GET")
    @GetMapping("/products")
    public ResponseEntity<List<ProductsModels>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productsRepositories.findAll());
    }

    @Operation(summary = "Pesquisar produtos existentes no banco de dados por ID", method = "GET")
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductsModels> product1 = productsRepositories.findById(id);
        if (product1.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto informado não existe");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product1.get());
    }

    @Operation(summary = "Adicionar produto no banco de dados", method = "POST")
    @PostMapping("/products")
    public ResponseEntity<?> saveProducts(@RequestBody @Valid ProductsDto productsDto) {
        Optional<ProductsModels> existingSku = productsRepositories.findBySku(productsDto.sku());
        if (existingSku.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SKU já informado em outro produto");
        }
        ProductsModels productsModels = new ProductsModels();
        BeanUtils.copyProperties(productsDto, productsModels);
        productsModels.setId(null);
        ProductsModels savedProduct = productsRepositories.save(productsModels);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @Operation(summary = "Atualizar produto no banco de dados", method = "PUT")
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductsDto productsDto) {
        Optional<ProductsModels> product1 = productsRepositories.findById(id);
        if (product1.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto informado não existe");
        }
        Optional<ProductsModels> existingSku = productsRepositories.findBySku(productsDto.sku());
        if (existingSku.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SKU já informado em outro produto");
        }
        var productModel = product1.get();
        copyNonNullProperties(productsDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productsRepositories.save(productModel));
    }

    @Operation(summary = "Excluir produto do banco de dados por ID", method = "DEL")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductsModels> product1 = productsRepositories.findById(id);
        if (product1.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto informado não existe");
        }
        productsRepositories.delete(product1.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado");
    }
}
