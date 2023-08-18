package com.cropdata.entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.cropdata.converter.OrderStatusEnumConverter;
import com.cropdata.serde.OrderStatusSerdeProvider;
import com.cropdata.value.OrderStatusVo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@BatchSize( size = 10)
@SQLDelete(sql = "update orders  set deleted = true where order_number = ?")
@Where(clause = "deleted = false")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderNumber;

	@NotNull
	private LocalDate shippedDate = LocalDate.now(ZoneId.systemDefault());

	// @NotNull(message = "Status cannot be null")
	// @Enumerated(EnumType.STRING)

	//@JsonIgnore
	@Column(name = "OrderStatusEnum", columnDefinition = "ENUM('PENDING', 'CONFIRMED', 'IN_PROGRESS', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'ACTIVATED', 'DEACTIVATED')")
	@Convert(converter = OrderStatusEnumConverter.class)
	//@JsonSerialize(using = OrderStatusSerdeProvider.class)

	//@JsonBackReference
	private OrderStatusVo orderStatusVo;

	@Size(max = 500, message = "Comments cannot be more than 500 characters")
	private String comments;

	private Integer customerNumber;
	
	private String username;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "customer_number_fk") // ,referencedColumnName = "orderId"
	@JsonBackReference
	private Customer customer;

	@BatchSize(size = 3)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	// @JoinColumn(name = "orderId_fk")
	@JoinColumn(name = "orderNumber", referencedColumnName = "orderNumber")
	private List<OrderDetails> orderDetails;

	@JsonIgnore
	private boolean deleted = Boolean.FALSE;

}
