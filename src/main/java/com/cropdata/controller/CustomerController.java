package com.cropdata.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cropdata.dto.CustomerDTO;
import com.cropdata.entity.Customer;
import com.cropdata.service.ICustomerService;
import com.cropdata.util.UtilTransper;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private UtilTransper utilTransper;
	
//	@Autowired
//	private JwtUtil jwtUtil;

//	@Autowired
//    private AuthenticationManager authenticationManager;
//	
	
//	@PostMapping("/post-2")
//	public ResponseEntity<CustomerAuthResponse> login(@RequestBody CustomerAuthRequest userRequest){
//		
//		String token=jwtUtil.generateToken(userRequest.getUserName());
//		return  ResponseEntity.ok(new CustomerAuthResponse(token,"success"));
//		
//	}
//	@PostMapping("/login")
//	public ResponseEntity<String> login(@RequestBody CustomerAuthRequest loginRequest) {
//	    Authentication authentication = authenticationManager.authenticate(
//	            new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
//
//	    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//	    String jwtToken = jwtUtil.generateJwtToken(authentication);
//
//	    HttpHeaders responseHeaders = new HttpHeaders();
//	    responseHeaders.set("Authorization", "Bearer " + jwtToken);
//
//	    return ResponseEntity.ok().headers(responseHeaders).body("Login successful!");
//	}

	
	
	

	@PostMapping()
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customer) {

		Customer customers = customerService.saveCustomer(utilTransper.getCustomer(customer));
		try {
			if (customers != null)
				System.out.println("Customer Created Successfully");
			else
				System.out.println("You are sending null valus	");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(customers);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<?>> findAllCustomer() {

		List<Customer> customers = customerService.getAllCustomer();
		List<CustomerDTO> dto = new ArrayList<>();
		for (Customer customer : customers) {
			dto.add(utilTransper.getCustomer(customer));
		}

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping("{customerNumber}")
	public ResponseEntity<CustomerDTO> findByCustomerNumber(@PathVariable Integer customerNumber) {

		Customer customer = customerService.getAllByCustomerNumber(customerNumber);
		CustomerDTO customerDTO = new CustomerDTO();
		try {

			customerDTO = utilTransper.getCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(customerDTO);

	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO) {

		Customer customers = customerService.updateCustomer(utilTransper.getCustomer(customerDTO));
		try {
			if (customers != null)
				System.out.println("Customer Updated Successfully");
			else
				System.out.println("You are sending null valus");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(customers);
	}

	@PatchMapping("/update/{customerNumber}")
	public ResponseEntity<?> updateCustomerBycustomerNumber(@PathVariable Integer customerNumber,
			@RequestBody Customer customer) {

		Customer customers = customerService.updateCustomerBycustomerNumber(customerNumber,customer);
		CustomerDTO customerDTO = new CustomerDTO();
		try {
			if (customers != null)
				customerDTO = utilTransper.getCustomer(customers);
			else
				System.out.println("You are sending null valus");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(customerDTO);
	}

	@DeleteMapping("{customerNumber}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerNumber) {

		Integer id = customerService.deleteCustomerBycustomerNumber(customerNumber).getCustomerNumber();

		return ResponseEntity.ok().body("Customer deleted" + id);
	}

}
