package com.cropdata.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	private String resourceType;
	private Integer orderNumber;
	private Object fieldValue;

	public ResourceNotFoundException(String resourceType, Integer orderNumber, Object fieldValue) {
		super(String.format("%s not found with %s : '%s'", resourceType, orderNumber, fieldValue));
		this.resourceType = resourceType;
		this.orderNumber = orderNumber;
		this.fieldValue = fieldValue;
	}

	public String getResourceType() {
		return resourceType;
	}

	public Integer getorderNumber() {
		return orderNumber;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
