package com.cropdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropdata.entity.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
