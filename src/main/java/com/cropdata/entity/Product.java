package com.cropdata.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.codecommit.model.FileMetadata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Builder
@Entity
@Data
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productCode;

	@NotBlank(message = "Product name cannot be blank")
	private String productName;

	private String productDescription;

	@Min(value = 0, message = "Quantity in stock must be at least 0")
	private Integer quantityInStock;

	@NotNull(message = "Price cannot be null")
	@DecimalMin(value = "100.00", message = "Price must be at least 100.00")
	private Double price;

//	@ManyToOne
//	private Customer customer;

	@OneToMany
	@JoinColumn(name = "orderNumber_fk")
	private List<Order> order;

	@Lob
	private MultipartFile file;

	@Transient
	private List<MultipartFile> files;
//	

}
