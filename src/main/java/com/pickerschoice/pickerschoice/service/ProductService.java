package com.pickerschoice.pickerschoice.service;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.Product;
import com.pickerschoice.pickerschoice.repository.ProductRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class ProductService {
    private static final String PRODUCT_NOT_FOUND = "Product with id %s not found";
    private ProductRepository productRepository;

    // FETCH ALL PRODUCTS
    @Transactional
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // FETCH A PRODUCT BY ID
    @Transactional
    public Product findProductById(int productId) {
        return productRepository
        		.findById(productId)
        		.orElseThrow(() -> new AppAuthException(String.format(PRODUCT_NOT_FOUND, productId)));
    }

    // SAVE PRODUCT
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // UPDATE A PRODUCT
    @Transactional
    public Product updateProduct(int productId, Product product) {
        Product _product = productRepository.findById(productId).orElseThrow(
                () -> new AppAuthException(String.format(PRODUCT_NOT_FOUND, productId))
        );

        _product.setProductId(_product.getProductId());
        _product.setProductName(product.getProductName());
        _product.setProductDescription(product.getProductDescription());
        _product.setImage(product.getImage());
        _product.setPrice(product.getPrice());

        return productRepository.save(_product);
    }

	public void deleteProduct(int productId) {
        Product _product = productRepository.findById(productId).orElseThrow(
                () -> new AppAuthException(String.format(PRODUCT_NOT_FOUND, productId))
        );
        
        productRepository.delete(_product);
	}
}
