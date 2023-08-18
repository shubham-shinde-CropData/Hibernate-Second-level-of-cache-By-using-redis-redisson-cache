package com.cropdata.entity;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
//@Table(name = "customer", uniqueConstraints = { @UniqueConstraint(name = "phone", columnNames = { "phone" }) })
@Table(name = "customer",uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@AllArgsConstructor
@RequiredArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerNumber")
	private Integer customerNumber;

	@Size(min = 3, max = 15, message = "Customer First Name Must be between 3 to 15")
	@NotBlank(message = "First name cannot be blank")
	// @Pattern(regexp = "^[A-Za-z]*$", message = "Invalid Input")
	private String customerFirstName;

	@Size(min = 3, max = 15, message = "Customer Last Name Must be between 3 to 15")
	@NotBlank(message = "Last name cannot be blank")
	// @Pattern(regexp = "^[A-Za-z]*$", message = "Invalid Input")
	private String customerLastName;

	@Size(min = 10, max = 10, message = "Mobile Number Must Be 10 Digit")
	// @Pattern(regexp = "^[0-9]{10}", message = "Invalid Mobile Number")
	@Column(unique = true)
	private String phone;

	@NotBlank(message = "Address line 1 cannot be blank")
	private String addressLine1;

	@Size(max = 50, message = "Address line 2 cannot be more than 50 characters")
	private String addressLine2;

	@NotBlank(message = "City cannot be blank")
	// @Pattern(regexp = "^[A-Z a-z]*$", message = "Invalid Input")
	private String city;

	@NotBlank(message = "State cannot be blank")
	// @Pattern(regexp = "^[A-Z a-z]*$", message = "Invalid Input")
	private String state;

	// @PostalCode
	private Integer postalCode;

	@NotBlank(message = "Country cannot be blank")
	// @Pattern(regexp = "^[A-Z a-z]*$", message = "Invalid Input")
	private String country;

	//@Column(nullable = false, unique = false)
	
	private String username;

	private String password;

	//private String token;
	
	private String email;

	@ElementCollection
	//@CollectionTable(name = "rolesTab", joinColumns = @JoinColumn(name = "customerNumber"))
	@CollectionTable(name = "rolesTab",joinColumns = @JoinColumn(referencedColumnName = "customerNumber"))
   // @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Column(name = "role")
	private Set<String> roles;

	

}
