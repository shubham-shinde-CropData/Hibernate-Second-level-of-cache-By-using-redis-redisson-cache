package com.cropdata.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropdata.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	// List<Order> findByOrderStatus(Status orderStatus);
	

	/*
	 * @Modifying
	 * 
	 * @Transactional
	 * 
	 * @Query("UPDATE Order o SET o.field1 = :field1 WHERE o.orderNumber = :orderNumber"
	 * ) Order updateOrderByOrderID(@Param("orderNumber") Integer
	 * orderNumber, @Param("order") Order order);
	 */
}
