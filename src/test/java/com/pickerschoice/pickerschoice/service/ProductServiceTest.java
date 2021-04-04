package com.pickerschoice.pickerschoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pickerschoice.pickerschoice.model.Product;
import com.pickerschoice.pickerschoice.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;	
	private ProductService productService;
	
	@BeforeEach
	public void setup() {
		productService = new ProductService(productRepository);
	}
	
	@Test
	@DisplayName("Should return all products")
	public void shouldReturnAllProducts() {
		List<Product> expectedProducts = new ArrayList<>();
		Product product1 = new Product(1, "Product", "Description", 100.0, "Image");
		Product product2 = new Product(2, "Product 2", "Description 2", 200.0, "Image 2");
		expectedProducts.add(product2);
		expectedProducts.add(product1);
		
		Mockito.when(productService.findAllProducts()).thenReturn(expectedProducts);
		
		List<Product> actualProducts = productService.findAllProducts();
		
		Assertions.assertThat(expectedProducts).isEqualTo(actualProducts);
	}
	
	@Test
	@DisplayName("Should retrieve a product by an ID")
	public void shouldFindProductById() {
		Product expectedProduct = new Product(1, "Product", "Description", 100.0, "Image");
		
		Mockito.when(productService.findProductById(1)).thenReturn(Optional.of(expectedProduct));
		
		Optional<Product> actualResponse = productService.findProductById(1);
		
		Assertions.assertThat(actualResponse.get().getProductId()).isEqualTo(expectedProduct.getProductId());
		Assertions.assertThat(actualResponse.get().getProductName()).isEqualTo(expectedProduct.getProductName());
	}
	
	@Test
	@DisplayName("Should save a product")
	public void shouldSaveProduct() {
		Product product = new Product("Product", "Description", 100.0, "Image");
		Product expectedProduct = new Product(1, "Product", "Description", 100.0, "Image");
		
		Mockito.when(productService.saveProduct(product)).thenReturn(expectedProduct);
		
		Product actualResponse = productRepository.save(product);
		Mockito.verify(productRepository, Mockito.times(1)).save(product);
		
		Assertions.assertThat(actualResponse.getProductId()).isEqualTo(expectedProduct.getProductId());
		Assertions.assertThat(actualResponse.getProductName()).isEqualTo(expectedProduct.getProductName());
	}

}
