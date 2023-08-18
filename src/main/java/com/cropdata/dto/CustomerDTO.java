package com.cropdata.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Integer customerNumber;

    @NotBlank(message = "Customer first name is required")
    @Size(min = 3, max = 15, message = "Customer first name must be between 3 and 15 characters")
    private String customerFirstName;

    @NotBlank(message = "Customer last name is required")
    @Size(min = 3, max = 15, message = "Customer last name must be between 3 and 15 characters")
    private String customerLastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be a 10-digit number")
    private String phone;

    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotNull(message = "Postal code is required")
    private Integer postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Username is required")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;
	
	private String email;

    private Set<String> roles;

}
