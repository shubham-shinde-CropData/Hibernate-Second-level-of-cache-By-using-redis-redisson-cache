package com.cropdata.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.cropdata.enums.OrderStatusEnum;
import com.cropdata.value.OrderStatusVo;
@Converter(autoApply = true)
public class OrderStatusEnumConverter implements AttributeConverter<OrderStatusVo, String> {

	@Override
    public OrderStatusVo convertToEntityAttribute(String value) {
        for (OrderStatusEnum orderStatus : OrderStatusEnum.values()) {
            if (orderStatus.getValue().equals(value)) {
                return new OrderStatusVo(value);
            }
        }
        throw new IllegalArgumentException("Invalid order status: " + value);
    }

	 @Override
	    public String convertToDatabaseColumn(OrderStatusVo orderStatusVo) {
	        return orderStatusVo.getValue().getValue();
	    }

}
