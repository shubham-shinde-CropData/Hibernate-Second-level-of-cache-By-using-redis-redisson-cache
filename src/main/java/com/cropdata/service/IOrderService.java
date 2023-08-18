package com.cropdata.service;

import java.util.List;
import java.util.Optional;

import com.cropdata.entity.Order;

public interface IOrderService {

	public Order saveOrder(Order order);

	public List<Order> getAllOrder();

	public void deleteOrder(Integer orderNumber);

	public Optional<Order> getOrderNumber(Integer orderNumber);

	public Order updateOrder(Order orderUpdate);

}
