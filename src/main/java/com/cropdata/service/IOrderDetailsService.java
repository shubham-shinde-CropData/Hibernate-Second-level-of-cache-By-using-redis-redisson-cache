package com.cropdata.service;

import java.util.List;

import com.cropdata.entity.OrderDetails;

public interface IOrderDetailsService {

	public OrderDetails saveOrderDetailse(OrderDetails orderDetails);

	public List<OrderDetails> getAllOrderDetails();

	public OrderDetails getOrderDetailsByOrderId(Integer orderId);

	public OrderDetails updateOrderDetails(OrderDetails orderDetails);

	public OrderDetails deleteOrderDetailsByOrderId(Integer orderId);

}
