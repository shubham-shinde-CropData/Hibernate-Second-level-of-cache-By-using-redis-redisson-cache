package com.cropdata.entity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orderDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;

	@Min(value = 1, message = "Quantity ordered must be at least 1")
	private Integer quantityOrdered;

	// @Min(value = 100, message = "Price each must be at least 100.00")
	private Double priceEach;

	@NotEmpty
	private int productcode;

	private Integer orderNumber;

//	@JsonIgnore
//	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
//	//@ManyToMany
//	 @JoinColumn(name = "orderNumber_fk")
//	private Order order;

	  @ManyToOne(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.DETACH})
      //@JoinColumns ({@JoinColumn(name = "orderNumber", referencedColumnName="orderNumber")})
      @JoinColumn(name = "orderNumber_fk")
      @JsonBackReference
      private Order order;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "productCode_fk")
	private Product product;
}