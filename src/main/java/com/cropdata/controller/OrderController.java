package com.cropdata.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cropdata.dto.OrderDTO;
import com.cropdata.entity.Order;
import com.cropdata.service.IOrderService;
import com.cropdata.serviceimpl.OrderDetailsServiceeImpl;
import com.cropdata.serviceimpl.OrderServiceImpl;
import com.cropdata.util.UtilTransper;

//@EnableSpringHttpSessio
@RestController
@RequestMapping("/order")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OrderController {

	@Autowired
	IOrderService orderService;

	@Autowired
	private OrderServiceImpl orderServiceImpl;

	@Autowired
	private OrderDetailsServiceeImpl orderDetailsServiceeImpl;

	@Autowired
	UtilTransper utilTransper;

	@PostMapping("/save")
	public ResponseEntity<?> createOrder(@RequestBody List<OrderDTO> orderDTO) {

		List<Order> order = new ArrayList<Order>();

		Order order2 = new Order();
		
		try {
			if (orderDTO != null) {
				for (OrderDTO orderDTOs : orderDTO) {
					order2 = orderService.saveOrder(utilTransper.toOrder(orderDTOs));
					order.add(order2);
				}
				System.out.println("Order  Created Successfully");
			} else {
				System.out.println("You are sending null valus");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(order);
	}


//
	@DeleteMapping("/{orderNumber}")
	public ResponseEntity<String> deleteOrder(@PathVariable Integer orderNumber) {
		ResponseEntity<String> resp = null;

		try {
			// boolean exist = orderService.isOrderExist(orderNumber);
			Order order = orderService.getOrderNumber(orderNumber).get();

			orderService.deleteOrder(orderNumber);
			resp = new ResponseEntity<String>("this :" + orderNumber + " : is Cancelled", HttpStatus.OK);
			System.out.println("---------------------CANCELLED-------------------");

			// resp = new ResponseEntity<String>("this " + orderNumber + "is not deleted",
			// HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			resp = new ResponseEntity<String>("annable to delete this " + orderNumber + " try another way...",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}

//	@PutMapping("/{orderNumber}")
//	public ResponseEntity<?> updateorder(@PathVariable("orderNumber") Integer orderNumber,
//			@RequestBody OrderDTO orderDTO) {
//
//		// Order existingOrder=orderService.updateOrder(utilTransper.toOrder(orderDTO));
//		Order existingOrder = orderServiceImpl.findOrderByOrderNumber(orderNumber);
//
//		if (existingOrder == null) {
//			return ResponseEntity.notFound().build();
//		}
//		// Update the order from the input OrderDTO
//		existingOrder.setComments(orderDTO.getComments());
//		
//		// Update the order details for each OrderDetailsDTO in the input list
//		List<OrderDetailsDTO> orderDetailsDTOList = orderDTO.getOrderDetails();
//		List<OrderDetails> orderDetailsList = new ArrayList<>();
//		for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOList) {
//			OrderDetails existingOrderDetails = orderServiceImpl
//					.findOrderDetailsByOrderId(orderDetailsDTO.getOrderId());
//			if (existingOrderDetails == null) {
//				return ResponseEntity.notFound().build();
//			}
//			existingOrderDetails.setQuantityOrdered(orderDetailsDTO.getQuantityOrdered());
//	        existingOrderDetails.setOrder(existingOrder);
//	        orderDetailsList.add(existingOrderDetails);
//		}
//		existingOrder.setOrderDetails(orderDetailsList);
//		   Order updatedOrder = orderService.saveOrder(existingOrder);
//		   Order updatedOrderDTO = utilTransper.toOrder(orderDTO);
//		return ResponseEntity.ok().body(existingOrder);
//
//	}

//	@PutMapping("/update")
//	public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO) {
//
//		Integer order = orderServiceImpl.updateOrder(utilTransper.toOrder(orderDTO));
//				
//		try {
//			if (order != null)
//				System.out.println("order Updated Successfully");
//			else
//				System.out.println("You are sending null valus");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return ResponseEntity.ok().body(order);
//	}

	@PutMapping("/update")
	public ResponseEntity<?> updateOrderByOrderNumber(@RequestBody OrderDTO orderDTO) {
		Order order = orderServiceImpl.updateOrder(utilTransper.toOrder(orderDTO));

		if (order != null) {
			System.out.println("Order  updated Successfully");
		} else {
			System.out.println("You are sending null valus");
		}

		return ResponseEntity.ok().body(order);

	}

	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAllOrders() {

		List<Order> orders = orderService.getAllOrder();
		List<OrderDTO> orderDTO = orders.stream().map(utilTransper::toOrderDTO).collect(Collectors.toList());
		if (orderDTO.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		System.out.println("----------------------get all order------------------------------");
		return ResponseEntity.ok().body(orderDTO);
	}

	@GetMapping("/{orderNumber}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable(value = "orderNumber") Integer orderNumber) {
		Optional<Order> order = orderService.getOrderNumber(orderNumber);
		if (!order.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		OrderDTO orderDTO = utilTransper.toOrderDTO(order.get());
		return ResponseEntity.ok().body(orderDTO);
	}

//	
//	@GetMapping("{orderNumber}")
//	public ResponseEntity<?> getOrderByOrderNumber(@PathVariable Integer orderNumber){
//		Order order=orderServiceImpl.getOrderByOrderNumber(orderNumber);
//		
//		OrderDTO orderDTO=new OrderDTO();
//		try {
//			 orderDTO=utilTransper.toOrderDTO(order);
//			 System.out.println("here");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	
//		return ResponseEntity.ok().body(orderDTO);
//
//	}
//	
//
//	@DeleteMapping("{orderNumber}")
//	public ResponseEntity<?> deleteOrderDetailsByOrderId(@PathVariable Integer orderNumber) {
//		Integer ordercode = orderServiceImpl.deleteOrderByOrderNumber(orderNumber).getOrderNumber();
//
//		return ResponseEntity.ok().body("Order Detailse deleted  :-" + ordercode);
//	}
//	
//	
//	

}
