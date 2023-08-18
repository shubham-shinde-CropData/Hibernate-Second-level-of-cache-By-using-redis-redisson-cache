	package com.cropdata.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdata.entity.Customer;
import com.cropdata.repo.CustomerRepository;
import com.cropdata.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	

	@Override
	public Customer saveCustomer(Customer customer) {

		Customer customers = new Customer();
		try {

			if (customer != null) {

				System.out.println(customer.getCustomerFirstName());
				customers = customerRepository.save(customer);

			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public List<Customer> getAllCustomer() {

		List<Customer> customers = new ArrayList<>();
		try {
			customers = customerRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Customer getAllByCustomerNumber(Integer customerNumber) {
		Customer customer = new Customer();
		try {
			if (customerNumber != null) {
				customer = customerRepository.findById(customerNumber).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {

		Customer customers = customerRepository.findById(customer.getCustomerNumber()).get();
		try {

			if (customer.getCustomerNumber() != null) {

				customers = customerRepository.save(customer);

			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Customer updateCustomerBycustomerNumber(Integer customerNumber, Customer customer) {

		Customer customers = customerRepository.findById(customerNumber).get();
		Customer customer2 = new Customer();
		try {
			if (customer.getCustomerNumber() != null) {
				customers.setAddressLine1(customer.getAddressLine1());
				customer2 = customerRepository.save(customers);
			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer2;
	}

	@Override
	public Customer deleteCustomerBycustomerNumber(Integer customerNumber) {

		Customer customers = customerRepository.findById(customerNumber).get();
		try {

			if (customers.getCustomerNumber() != null) {

				customerRepository.delete(customers);

			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	
	

	
	
	
	
	
}
