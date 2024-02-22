package com.example.API.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.*;
import com.example.API.models.ProductsModels;
import com.example.API.dtos.ProductsDto;
import com.example.API.repositories.ProductsRepositories;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProductsControllersTest {

    private ProductsControllers productsControllers;
    private ProductsRepositories productsRepositories;

    @BeforeEach
    public void setUp() {
        productsRepositories = mock(ProductsRepositories.class);
        productsControllers = new ProductsControllers(productsRepositories);
    }



    @Test
    public void testGetProducts() {
        List<ProductsModels> productList = new ArrayList<>();
        when(productsRepositories.findAll()).thenReturn(productList);
        ResponseEntity<List<ProductsModels>> response = productsControllers.getProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productList, response.getBody());
    }

    @Test
    public void testGetProduct() {
        UUID productId = UUID.randomUUID();
        ProductsModels product = new ProductsModels();
        when(productsRepositories.findById(productId)).thenReturn(Optional.of(product));
        ResponseEntity<Object> response = productsControllers.getProduct(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testSaveProduct() {
        ProductsDto productDto = new ProductsDto(new BigDecimal(10.10) ,"22/02","abcdef","123456789","1");
        ProductsModels productModel = new ProductsModels();
        when(productsRepositories.save(any(ProductsModels.class))).thenReturn(productModel);
        ResponseEntity<?> response = productsControllers.saveProducts(productDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() {
        UUID productId = UUID.randomUUID();
        ProductsDto updatedProductDto = new ProductsDto(new BigDecimal(10.10) ,"22/02","abcdef","123456789","1");
        ProductsModels existingProduct = new ProductsModels();
        when(productsRepositories.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productsRepositories.save(any(ProductsModels.class))).thenReturn(existingProduct);
        ResponseEntity<Object> response = productsControllers.updateProduct(productId, updatedProductDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testDeleteProduct() {
        UUID productId = UUID.randomUUID();
        ProductsModels product = new ProductsModels();
        when(productsRepositories.findById(productId)).thenReturn(Optional.of(product));
        ResponseEntity<Object> response = productsControllers.deleteProduct(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
