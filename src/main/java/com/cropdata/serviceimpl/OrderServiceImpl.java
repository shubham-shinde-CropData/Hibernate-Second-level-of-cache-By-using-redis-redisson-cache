package com.cropdata.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdata.entity.Order;
import com.cropdata.entity.OrderDetails;
import com.cropdata.exception.ResourceNotFoundException;
import com.cropdata.repo.OrderDetailsRepository;
import com.cropdata.repo.OrderRepository;
import com.cropdata.repo.ProductRepository;
import com.cropdata.service.IOrderService;
import com.cropdata.service.IProductService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired

	private OrderDetailsRepository orderDetailsRepository;

	@Autowired

	ProductRepository productRepository;

	@Autowired
	ProductServiceImpl productServiceImpl;

	@Autowired
	private IProductService iProductService;

	@Override
	public Order saveOrder(Order order) {

		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrder() {

		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Integer orderNumber) {
		Optional<Order> optionalOrder = orderRepository.findById(orderNumber);
		Order orders = null;

		if (optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			order.setOrderStatusVo(order.getOrderStatusVo());
			System.out.println("cancelled");
			orderRepository.save(order);

			// Update the quantity of each product that was ordered in this order

			for (OrderDetails orderDetails : order.getOrderDetails()) {
				int productCode = orderDetails.getProductcode();
				int quantityOrdered = orderDetails.getQuantityOrdered();
				iProductService.incrementdecrementProductQuantityInStock(productCode, "increment", quantityOrdered);
			}

			orderRepository.delete(order);
			System.out.println("Order with order number " + orderNumber + " has been deleted");
		} else {
			throw new ResourceNotFoundException("Order with order number " + orderNumber + " not found", orderNumber,
					orders);
		}
	}

	@Override
	public Optional<Order> getOrderNumber(Integer orderNumber) {

		return orderRepository.findById(orderNumber);
	}

	@Override
	public Order updateOrder(Order order) {

		System.out.println(order.getOrderNumber());
		Order orders = orderRepository.findById(order.getOrderNumber()).get();
		System.out.println(orders.getCustomerNumber());
		System.out.println("--------------------");
		try {

			if (order.getOrderNumber() != null) {

				orders = orderRepository.save(order);

			} else {
				System.out.println("You are sending null valus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;

	}

}
