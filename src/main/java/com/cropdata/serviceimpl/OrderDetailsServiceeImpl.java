package com.cropdata.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdata.entity.Customer;
import com.cropdata.entity.OrderDetails;
import com.cropdata.repo.OrderDetailsRepository;
import com.cropdata.service.IOrderDetailsService;

@Service
public class OrderDetailsServiceeImpl implements IOrderDetailsService {

	
	
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Override
	public OrderDetails saveOrderDetailse(OrderDetails orderDetails) {

		OrderDetails orderDetailss = new OrderDetails();
		try {

			if (orderDetails != null) {

				orderDetailss = orderDetailsRepository.save(orderDetails);

			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDetailss;
	}

	@Override
	public List<OrderDetails> getAllOrderDetails() {
		List<OrderDetails> orderDetails = new ArrayList<>();
		try {
			orderDetails = orderDetailsRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDetails;
	}

	@Override
	public OrderDetails getOrderDetailsByOrderId(Integer orderId) {
		OrderDetails orderDetails = new OrderDetails();
		try {
			if (orderId != null) {
				orderDetails = orderDetailsRepository.findById(orderId).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderDetails;
	}

	@Override
	public OrderDetails updateOrderDetails(OrderDetails orderDetails) {
		OrderDetails orderDetailss = orderDetailsRepository.findById(orderDetails.getOrderId()).get();
		try {

			if (orderDetails.getOrderId() != null) {

				orderDetailss = orderDetailsRepository.save(orderDetails);

			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDetailss;
	}

	@Override
	public OrderDetails deleteOrderDetailsByOrderId(Integer orderId) {
		OrderDetails orderDetails = orderDetailsRepository.findById(orderId).get();
		try {

			if (orderDetails.getOrderId() != null) {

				orderDetailsRepository.delete(orderDetails);

			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderDetails;
	}

}
