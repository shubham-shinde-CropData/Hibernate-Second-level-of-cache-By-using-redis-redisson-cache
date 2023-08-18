package com.cropdata.faker;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.cropdata.entity.Customer;
import com.cropdata.repo.CustomerRepository;
import com.github.javafaker.Faker;

@Component
@Order(2)
public class CustomerInitializer {

	@Autowired
	private CustomerRepository customerRepository;

	private final Faker faker = new Faker();

	@PostConstruct
	public void initialize() {

		if (customerRepository.count() == 0) {
			for (int i = 0; i < 50; i++) {

				Customer customer = new Customer();

				customer.setCustomerFirstName(faker.name().firstName());
				customer.setCustomerLastName(faker.name().lastName()); // //
				customer.setPhone(faker.phoneNumber().cellPhone());
				customer.setAddressLine1(faker.address().streetAddress());
				customer.setAddressLine2(faker.address().secondaryAddress());
				customer.setCity(faker.address().city());
				customer.setUsername(faker.name().firstName().toLowerCase() + faker.name().lastName().toLowerCase());
				customer.setPassword(faker.internet().password());
				customer.setState(faker.address().state());
				customer.setPostalCode(faker.number().numberBetween(100000, 999999));
				customer.setCountry(faker.address().country());
				customerRepository.save(customer);
			}
		}
	}
}
