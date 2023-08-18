package com.cropdata.exception;

public class OrderDetailsEmptyException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderDetailsEmptyException() {
        super("Order details cannot be empty");
    }
    
    public OrderDetailsEmptyException(String message) {
        super(message);
    }
}
    
