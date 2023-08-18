package com.cropdata.service;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.cropdata.entity.Product;

public interface IProductService {

	public List<Product> getallproducts();

	public Product saveproducts(Product product);

	Product incrementdecrementProductQuantityInStock(Integer productCode, String value, Integer orderQuantity);

	Product findProductById(Integer productCode);
	public Product updateProduct(Product product);
	public byte[] downloadFile(String filename);
	public Product createProduct(@Valid Product product);

	Product uploadImages(Product product) throws IOException;

	String deleteProduct(Integer productCode);
	public String deleteFilesByProductName(String productName);

	public String deleteFilesByProductCode(Integer productCode);

	Product updateProductQuantityInStock(Integer productCode, Integer quantityOrdered);

	Product updateProductById(Integer productCode, Product product);

	//public String uploadFile(MultipartFile file);

	//Product uploadImages(Product product) throws IOException;
	
	//public Product uploadImage(Product product, MultipartFile file) throws IOException;

	//String deleteFilesByProductName(String productName);

//	String deleteFilesByProductCode(Integer productCode);
}
