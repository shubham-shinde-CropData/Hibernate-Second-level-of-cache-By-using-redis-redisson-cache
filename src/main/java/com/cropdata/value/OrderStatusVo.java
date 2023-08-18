package com.cropdata.value;

import com.cropdata.enums.OrderStatusEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Schema(implementation = OrderStatusEnum.class, description = "Order Status")
//@JsonSerialize(using = OrderStatusSerdeProvider.class)
public class OrderStatusVo {

	OrderStatusEnum value;

	public OrderStatusVo(String dbData) {

		value = OrderStatusEnum.valueOf(dbData);

	}

//	@Override
//	public String toString() {
//		return "OrderStatusVo :" + value ;
//	}

	@Override
	public String toString() {
		return value.toString();
	}

	
}