package com.cropdata.dto;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.cropdata.entity.OrderDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
	private Integer orderId;

	private Integer quantityOrdered;

	private Double priceEach;

	private Integer productCode;


	private Integer orderNumber;


//	@ManyToOne(fetch = FetchType.LAZY)
//	private OrderDTO orderDTO;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "order_number")
//	private OrderDTO orderDTO;

}
