package com.cropdata.faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cropdata.entity.Customer;
import com.cropdata.entity.Order;
import com.cropdata.entity.OrderDetails;
import com.cropdata.repo.OrderRepository;
import com.cropdata.value.OrderStatusVo;
import com.github.javafaker.Faker;

@Component
@org.springframework.core.annotation.Order(3)
public class OrderFakerInitializer implements InitializingBean {

	private final Faker faker = new Faker();

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (orderRepository.count() == 0) {

			for (int i = 0; i < 50; i++) {

				Order order = new Order();

				order.setShippedDate(LocalDate.now(ZoneId.systemDefault()));

				order.setOrderStatusVo(new OrderStatusVo(faker.options().option("PENDING", "CONFIRMED", "IN_PROGRESS",
						"SHIPPED", "DELIVERED", "CANCELLED", "ACTIVATED", "DEACTIVATED")));

				order.setComments(faker.lorem().sentence());

				order.setDeleted(false);

				// order.setComments(faker.lorem().sentence());
				order.setCustomerNumber(faker.number().numberBetween(1, 1000));
				Customer customer = new Customer();
				OrderDetails orderDetails = new OrderDetails();
				order.setShippedDate(faker.date().future(24, TimeUnit.HOURS).toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate());
				// order.setShippedDate(faker.date().future(30, null, null));
				// order.setComments(faker.lorem().sentence());
				Integer number = faker.number().numberBetween(1, 1000);
				// customer.setCustomerNumber(number);
				// order.setCustomer(customer);

				orderRepository.save(order);
			}
		}
	}

}
