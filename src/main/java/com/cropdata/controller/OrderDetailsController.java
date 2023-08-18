package com.cropdata.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cropdata.dto.OrderDetailsDTO;
import com.cropdata.entity.OrderDetails;
import com.cropdata.service.IOrderDetailsService;
import com.cropdata.util.UtilTransper;

@RestController
@RequestMapping("/orderdetailse")
public class OrderDetailsController {

	@Autowired
	private IOrderDetailsService iOrderDetailsService;

	@Autowired
	private UtilTransper utilTransper;

	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody OrderDetailsDTO orderDetailsDTO) {

		OrderDetails orderDetails = iOrderDetailsService
				.saveOrderDetailse(utilTransper.getOrderDetails(orderDetailsDTO));
		try {
			if (orderDetails != null)
				System.out.println("Order Details Created Successfully");
			else
				System.out.println("You are sending null valus");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(orderDetails);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<?>> findAllOrderDetails() {

		List<OrderDetails> orderDetailss = iOrderDetailsService.getAllOrderDetails();
		List<OrderDetailsDTO> dto = new ArrayList<>();
		for (OrderDetails orderDetails : orderDetailss) {
			dto.add(utilTransper.getOrderDetailsDTO(orderDetails));
		}

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping("{orderId}")
	public ResponseEntity<?> findAllOrderDetailsByOrderId(@PathVariable Integer orderId) {

		OrderDetails orderDetails = iOrderDetailsService.getOrderDetailsByOrderId(orderId);
		OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
		try {

			orderDetailsDTO = utilTransper.getOrderDetailsDTO(orderDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(orderDetailsDTO);

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) {

		OrderDetails orderDetails = iOrderDetailsService
				.updateOrderDetails(utilTransper.getOrderDetails(orderDetailsDTO));
		try {
			if (orderDetails != null)
				System.out.println("Customer Updated Successfully");
			else
				System.out.println("You are sending null valus");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(orderDetails);
	}

	@DeleteMapping("{orderId}")
	public ResponseEntity<?> deleteOrderDetailsByOrderId(@PathVariable Integer orderId) {
		Integer id = iOrderDetailsService.deleteOrderDetailsByOrderId(orderId).getOrderId();

		return ResponseEntity.ok().body("Order Detailse deleted" + id);
	}
}
