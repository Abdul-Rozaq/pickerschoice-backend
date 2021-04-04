package com.pickerschoice.pickerschoice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;

import com.pickerschoice.pickerschoice.model.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
	@SuppressWarnings("rawtypes")
	static MySQLContainer mySqlContainer = (MySQLContainer) new MySQLContainer("mysql:latest")
			.withDatabaseName("pickerschoice_db")
			.withUsername("testUser")
			.withPassword("pass");
	
	@Autowired
	private ProductRepository productRepository;

	@Test
	@DisplayName("Should create a new product in the database")
	public void shouldSaveProduct() {
		Product expectedProduct = new Product("Product", "Description", 100.0, "Image");
		Product actualProduct = productRepository.save(expectedProduct);
		
		assertThat(actualProduct)
			.usingRecursiveComparison()
			.ignoringFields("product_id")
			.isEqualTo(expectedProduct);
	}
}
