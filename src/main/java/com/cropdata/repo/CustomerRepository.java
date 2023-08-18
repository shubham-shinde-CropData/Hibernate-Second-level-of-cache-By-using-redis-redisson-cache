package com.cropdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cropdata.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{


	 //@Query("SELECT c FROM Customer c WHERE c.username = :username")
	//Customer findByUsername(String username);

}
