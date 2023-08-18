package com.cropdata.service;

import java.util.List;

import com.cropdata.entity.Customer;

public interface ICustomerService {

	public Customer saveCustomer(Customer customer);

	public List<Customer> getAllCustomer();

	public Customer getAllByCustomerNumber(Integer customerNumber);

	public Customer updateCustomer(Customer customer);

	public Customer updateCustomerBycustomerNumber(Integer customerNumber,Customer customer);

	public Customer deleteCustomerBycustomerNumber(Integer customerNumber);

}
