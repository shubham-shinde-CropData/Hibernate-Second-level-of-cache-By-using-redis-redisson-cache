package com.cropdata.faker;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cropdata.entity.Product;
import com.cropdata.repo.ProductRepository;
import com.github.javafaker.Faker;

@Component
@Order(3)
public class ProductInitializer implements InitializingBean {

	@Autowired
	private ProductRepository productRepository;

	private final Faker faker = new Faker();

	@Override
	public void afterPropertiesSet() throws Exception {
		if (productRepository.count() == 0) {
			for (int i = 0; i < 50; i++) {
				Product product = new Product();
				product.setProductName(faker.commerce().productName());
				product.setProductDescription(faker.lorem().sentence());
				product.setQuantityInStock(faker.number().numberBetween(1, 100));
				product.setPrice(faker.number().randomDouble(2, 1000, 10000));
				productRepository.save(product);
			}
		}
	}
}
