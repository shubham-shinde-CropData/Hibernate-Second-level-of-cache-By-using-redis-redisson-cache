package com.cropdata.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Integer productCode;

	private String productName;

	private String productDescription;

	private Integer quantityInStock;

	private Double price;

	//private int customerNumber;

	private MultipartFile file;

	private List<MultipartFile> files;

}
