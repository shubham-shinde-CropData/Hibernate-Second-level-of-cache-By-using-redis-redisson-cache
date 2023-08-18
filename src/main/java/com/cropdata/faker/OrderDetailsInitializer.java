package com.cropdata.faker;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cropdata.entity.OrderDetails;
import com.cropdata.entity.Product;
import com.cropdata.repo.OrderDetailsRepository;
import com.github.javafaker.Faker;

@Component
@Order(3)
public class OrderDetailsInitializer {

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	private final Faker faker = new Faker();

	@PostConstruct
	public void initialize() {
		if (orderDetailsRepository.count() == 0) {
			for (int i = 0; i < 50; i++) {

				OrderDetails orderDetails = new OrderDetails();

				// orderDetails.setOrderNumber(order.getOrderNumber());
				// orderDetails.setQuantityOrdered(faker.number().numberBetween(1, 9));
				Product product = new Product();
				// orderDetails.setProductCode(product.getProductCode()); //
				// orderDetails.setProductCode(faker.number().randomDigit()); //
				orderDetails.setPriceEach(faker.number().randomDouble(2, 100, 1000));
				orderDetailsRepository.save(orderDetails);
			}
		}
	}
}
