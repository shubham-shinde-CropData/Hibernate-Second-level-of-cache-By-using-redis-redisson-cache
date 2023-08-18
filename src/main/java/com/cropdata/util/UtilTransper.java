package com.cropdata.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cropdata.dto.CustomerDTO;
import com.cropdata.dto.OrderDTO;
import com.cropdata.dto.OrderDetailsDTO;
import com.cropdata.dto.ProductDTO;
import com.cropdata.entity.Customer;
import com.cropdata.entity.Order;
import com.cropdata.entity.OrderDetails;
import com.cropdata.entity.Product;
import com.cropdata.exception.CustomerNotFoundException;
import com.cropdata.repo.CustomerRepository;
import com.cropdata.repo.OrderDetailsRepository;
import com.cropdata.repo.OrderRepository;
import com.cropdata.repo.ProductRepository;
import com.cropdata.service.ICustomerService;
import com.cropdata.service.IOrderService;
import com.cropdata.service.IProductService;

@Component
public class UtilTransper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private IOrderService iOrderService;

	@Autowired
	ICustomerService iCustomerService;

	@Autowired
	IProductService iProductService;

	

	public Customer getCustomer(CustomerDTO dto) {

		Customer customer = new Customer();
		customer.setCustomerNumber(dto.getCustomerNumber());
		customer.setCustomerFirstName(dto.getCustomerFirstName());
		customer.setCustomerLastName(dto.getCustomerLastName());
		customer.setUsername(dto.getUserName());
		customer.setPhone(dto.getPhone());
		customer.setAddressLine1(dto.getAddressLine1());
		customer.setAddressLine2(dto.getAddressLine2());
		customer.setCity(dto.getCity());
		customer.setPassword(dto.getPassword());
		customer.setState(dto.getState());
		customer.setCountry(dto.getCountry());
		customer.setPostalCode(dto.getPostalCode());
		return customer;
	}

	public CustomerDTO getCustomer(Customer customer) {

		CustomerDTO dto = new CustomerDTO();

		dto.setCustomerNumber(customer.getCustomerNumber());
		dto.setCustomerFirstName(customer.getCustomerFirstName());
		dto.setCustomerLastName(customer.getCustomerLastName());
		dto.setAddressLine1(customer.getAddressLine1());
		dto.setAddressLine2(dto.getAddressLine2());
		dto.setUserName(customer.getUsername());
		// dto.setPassword(customer);
		dto.setCity(customer.getCity());
		dto.setState(customer.getState());
		dto.setCountry(customer.getCountry());
		dto.setPhone(customer.getPhone());
		dto.setPostalCode(customer.getPostalCode());

		return dto;
	}

//======================================================================================================================------------------------------------------------------------------------------------------------------------=======
	public Order toOrder(OrderDTO dto) {
		Order order = new Order();

		order.setOrderNumber(dto.getOrderNumber());
		order.setComments(dto.getComments());
		order.setShippedDate(dto.getShippedDate());

		// order.setOrderStatusVo(dto.getOrderStatusVo().toString());
		order.setOrderStatusVo(dto.getOrderStatusVo());

		try {

			if (dto.getCustomerNumber() != null) {
				order.setCustomer(customerRepository.findById(dto.getCustomerNumber()).get());
				order.setCustomerNumber(order.getCustomer().getCustomerNumber());
				System.out.println("-------------------" + order.getCustomerNumber());
				orderRepository.save(order);

			} else {
				throw new CustomerNotFoundException("put the valid customer number");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

//		List<Order> pendingOrders = orderRepository.findByOrderStatus(Status.PENDING);
//		List<Order> inProgressOrders = orderRepository.findByOrderStatus(Status.IN_PROGRESS);
//		List<Order> completedOrders = orderRepository.findByOrderStatus(Status.DELIVERED);

		// Perform further operations with the retrieved orders
//
//		for (Order order1 : inProgressOrders) {
//		    // Update the order status or perform other actions
//		    order1.setStatus(Status.CONFIRMED);
//		    // Save the updated order using the repository
//		    orderRepository.save(order);
//		}

		orderRepository.save(order);
//		
		List<OrderDetails> orderdetails = new ArrayList<>();
		for (OrderDetailsDTO orderDetails2 : dto.getOrderDetails()) {
			OrderDetails orderDetails = getOrderDetails(orderDetails2);
			orderDetails.setOrderNumber(order.getOrderNumber());
			orderdetails.add(orderDetails);
		}

		order.setOrderDetails(orderdetails);
		orderDetailsRepository.saveAll(orderdetails);

		return order;
	}
//--------------------------------------------------------------------------------------------------------------

	public OrderDTO toOrderDTO(Order order) {
		OrderDTO dto = new OrderDTO();
		dto.setComments(order.getComments());
		dto.setShippedDate(order.getShippedDate());
		dto.setOrderNumber(order.getOrderNumber());
		dto.setOrderStatusVo(order.getOrderStatusVo());

		// dto.setOrderStatusVo(order.getOrderStatusVo().getValue().name());

		try {

			if (order.getCustomer() != null) {
				// order.setCustomer(customerRepository.findById(dto.getCustomerNumber()).get());
				dto.setCustomerNumber(order.getCustomer().getCustomerNumber());
				System.out.println("-------------------" + order.getCustomerNumber());

			} else {
				throw new CustomerNotFoundException("put the valid customer number");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();

		if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
			for (OrderDetails orderDetails : order.getOrderDetails()) {
				OrderDetailsDTO orderDetailsDTO = getOrderDetailsDTO(orderDetails);
				System.out.println("am there");
				orderDetailsDTOs.add(orderDetailsDTO);
			}
			dto.setOrderDetails(orderDetailsDTOs);
		} else {
			dto.setOrderDetails(orderDetailsDTOs);
		}

		return dto;
	}

//--------------------------------------------------------------------------------------------------------------
	public OrderDetails getOrderDetails(OrderDetailsDTO detailsDTO) {
		OrderDetails orderDetails = new OrderDetails();

		Order order1 = new Order();

		System.out.println("order is going and order details getting the order id");

		if (detailsDTO.getProductCode() != null) {
			orderDetails.setProduct(productRepository.findById(detailsDTO.getProductCode()).get());
			orderDetails.setProductcode(orderDetails.getProduct().getProductCode());
			orderDetails.setPriceEach(orderDetails.getProduct().getPrice());

			if (orderDetails.getProduct().getQuantityInStock() >= detailsDTO.getQuantityOrdered()) {
				iProductService.incrementdecrementProductQuantityInStock(orderDetails.getProduct().getProductCode(),
						"decrement", detailsDTO.getQuantityOrdered());
			}
		}

		if (detailsDTO.getOrderNumber() != null) {
			orderDetails.setOrder(orderRepository.findById(detailsDTO.getOrderNumber()).get());
		}
		orderDetails.setQuantityOrdered(detailsDTO.getQuantityOrdered());

		return orderDetails;
	}

//====================================================================================================================----------------------------------------------------------------------------------------------------------------------

	public OrderDetailsDTO getOrderDetailsDTO(OrderDetails orderDetails) {

		OrderDetailsDTO detailsDTO = new OrderDetailsDTO();
		ArrayList<OrderDetails> list = new ArrayList<>();
		detailsDTO.setPriceEach(orderDetails.getPriceEach());
		detailsDTO.setOrderId(orderDetails.getOrderId());
		detailsDTO.setPriceEach(orderDetails.getPriceEach());
		if (orderDetails.getOrderNumber() != null) {
			detailsDTO.setOrderNumber(orderDetails.getOrderNumber());
		}
		if (orderDetails.getProductcode() != 0) {
			detailsDTO.setProductCode(orderDetails.getProductcode());
		}
		detailsDTO.setQuantityOrdered(orderDetails.getQuantityOrdered());

		return detailsDTO;

	}


//-====================================================================================================================---------------------------------------------------------------------------------------------------------------------
	public Product getProduct(ProductDTO dto) {
        Product product = new Product();
        product.setProductCode(dto.getProductCode());
        product.setProductName(dto.getProductName());
        product.setProductDescription(dto.getProductDescription());
        product.setQuantityInStock(dto.getQuantityInStock());
        product.setPrice(dto.getPrice());
        product.setFile(dto.getFile());
        product.setFiles(dto.getFiles());
//        if (dto.getCustomerNumber() != 0) { 
//            // product.setCustomer(customerRepository.findById(dto.getCustomerNumber()).get());
//        	
//        	System.out.println(product.getProductName());
//        	System.out.println("shubham");
//        }
        productRepository.save(product);
        return product;
    }

	public ProductDTO getProduct(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setProductCode(product.getProductCode());
		dto.setProductName(product.getProductName());
		dto.setProductDescription(product.getProductDescription());
		dto.setQuantityInStock(product.getQuantityInStock());
		dto.setPrice(product.getPrice());
		//dto.setCustomerNumber(product.);
//		if (product.getCustomer() != null) {
//			dto.setCustomerNumber(product.getCustomer().getCustomerNumber());
//		}
		return dto;
	}

	

}
