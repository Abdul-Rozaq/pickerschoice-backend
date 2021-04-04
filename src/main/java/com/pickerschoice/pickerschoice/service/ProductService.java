package com.pickerschoice.pickerschoice.service;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.Product;
import com.pickerschoice.pickerschoice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class ProductService {
    private static final String PRODUCT_NOT_FOUND = "Product with id %s not found";
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // FETCH ALL PRODUCTS
    @Transactional
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // FETCH A PRODUCT BY ID
    @Transactional
    public Optional<Product> findProductById(int productId) {
        return productRepository.findById(productId);
    }

    // SAVE PRODUCT
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // UPDATE A PRODUCT
    @Transactional
    public Product updateProduct(int productId, Product product) {
        Product _Product = productRepository.findById(productId).orElseThrow(
                () -> new AppAuthException(String.format(PRODUCT_NOT_FOUND, productId))
        );

        _Product.setProductId(product.getProductId());
        _Product.setProductName(product.getProductName());
        _Product.setProductDescription(product.getProductDescription());
        _Product.setImage(product.getImage());
        _Product.setPrice(product.getPrice());

        return productRepository.save(_Product);
    }
}
