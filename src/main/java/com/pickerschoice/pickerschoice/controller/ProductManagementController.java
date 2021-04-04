package com.pickerschoice.pickerschoice.controller;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.Product;
import com.pickerschoice.pickerschoice.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("management/api/v1/products")
public class ProductManagementController {
    private static final String PRODUCT_NOT_FOUND = "Product with id %s not found";
    private ProductService productService;

    @Autowired
    public ProductManagementController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") int productId) {
    	Optional<Product> product = productService.findProductById(productId);
//    	Optional<Product> product = productDao.findProductById(productId);
    	if (product.isEmpty()) {
    		throw new AppAuthException(String.format(PRODUCT_NOT_FOUND, productId));
    	}
        return new ResponseEntity<>( product.get(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") int productId, @RequestBody Product product) {
        return new ResponseEntity<>(productService.updateProduct(productId, product), HttpStatus.CREATED);
    }
}
