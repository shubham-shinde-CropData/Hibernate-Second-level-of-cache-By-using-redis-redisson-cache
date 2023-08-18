package com.cropdata.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.codecommit.model.FileMetadata;
import com.amazonaws.services.wafv2.model.CustomResponse;
import com.cropdata.customeresponse.CustomeResponse;
import com.cropdata.dto.ProductDTO;
import com.cropdata.entity.Product;
import com.cropdata.exception.ProductNotFoundException;
import com.cropdata.service.IProductService;
import com.cropdata.serviceimpl.ProductServiceImpl;
import com.cropdata.util.UtilTransper;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/product")
public class ProductController {

	private String code;

	private Object data;

	@Autowired
	private IProductService productService;

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private UtilTransper utilTransper;
	// this is my code of s3

//	@PostMapping("/upload")
//	public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
//		return new ResponseEntity<>(productServiceImpl.uploadFile(file),org.springframework.http.HttpStatus.OK);
//		
//	}
//	
	@PostMapping("/upload/{productCode}")
	public ResponseEntity<String> uploadFile(@PathVariable Integer productCode,
			@RequestParam("file") MultipartFile file) {
		// Retrieve the Product from the database
		Product product = productServiceImpl.findProductById(productCode);
		if (product == null) {
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		}

		// Upload the file and update the Product
		String uploadResult = productServiceImpl.uploadFile(product, file);

		return new ResponseEntity<>(uploadResult, HttpStatus.OK);
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String filename) {
		byte[] data = productServiceImpl.downloadFile(filename);
		ByteArrayResource byteArrayResource = new ByteArrayResource(data);
		return ResponseEntity.ok().contentLength(data.length).header("content-type", "application/octet-stream")
				.header("content-desposition", "attachment; filename=\"" + filename + "\"").body(null);

	}

	// add one product to table
	@SuppressWarnings("finally")
	@PostMapping("uploadimage")
	public ResponseEntity<Object> addOneProduct(@Valid @ModelAttribute Product product)
			throws ProductNotFoundException {
		try {
			Product product1 = productService.createProduct(product);
			data = product1;
			code = "CREATED";
		} catch (RuntimeException runtimeException) {
			data = null;
			code = "RUNTIME_EXCEPTION";
		} catch (Exception exception) {
			data = null;
			code = "EXCEPTION";
		} finally {
			return CustomeResponse.response(code, data);
		}
	}

	@DeleteMapping("/delete/{filename}")
	public ResponseEntity<String> deleteFile(@PathVariable String filename) {

		return new ResponseEntity<>(productServiceImpl.deleteFile(filename), org.springframework.http.HttpStatus.OK);

	}

	@SuppressWarnings("finally")
	@PostMapping(path = "uploadImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadProductImages1(Product product) {
		try {
			Product productImages = productService.uploadImages(product);
			data = productImages;
			code = "CREATED";
		} catch (AmazonServiceException e) {
			data = null;
			code = "RUNTIME_EXCEPTION";
		} catch (Exception e) {
			data = null;
			code = "EXCEPTION";
		} finally {
			return CustomeResponse.response(code, data);
		}
	}

//	@SuppressWarnings("finally")
//	@PostMapping(path = "uploadImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<?> uploadProductImages1(ProductDTO productDTO) {
//		try {
//			Product productImages = productService.uploadImages(utilTransper.getProduct(productDTO));
//			data = productImages;
//			code = "CREATED";
//		} catch (AmazonServiceException e) {
//			data = null;
//			code = "RUNTIME_EXCEPTION";
//		} catch (Exception e) {
//			data = null;
//			code = "EXCEPTION";
//		} finally {
//			return CustomeResponse.response(code, data);
//		}
//	}

//	@PostMapping(path = "uploadImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<?> uploadProductImages(@Valid ProductDTO productDTO, BindingResult bindingResult) {
//	    if (bindingResult.hasErrors()) {
//	        // Handle validation errors
//	        List<FieldError> errors = bindingResult.getFieldErrors();
//	        List<String> errorMessages = new ArrayList<>();
//	        for (FieldError error : errors) {
//	            errorMessages.add(error.getDefaultMessage());
//	        }
//	        return ResponseEntity.badRequest().body(errorMessages);
//	    }
//
//	    try {
//	        Product productImages = productService.uploadImages(utilTransper.getProduct(productDTO));
//	        return ResponseEntity.status(HttpStatus.CREATED).body(productImages);
//	    } catch (AmazonServiceException e) {
//	        // Handle Amazon service exception
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Amazon service exception occurred");
//	    } catch (Exception e) {
//	        // Handle other exceptions
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception occurred");
//	    }
//	}

	@SuppressWarnings("finally")
	@DeleteMapping("/deleteByCode/{productCode}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer productCode) {
		try {
			String product = productService.deleteFilesByProductCode(productCode);

			data = product;
			code = "SUCCESS";
		} catch (AmazonServiceException e) {
			data = null;
			code = "RUNTIME_EXCEPTION";
		} catch (Exception e) {
			data = null;
			code = "EXCEPTION";
		} finally {
			return CustomeResponse.response(code, data);
		}
	}

	@SuppressWarnings("finally")
	@DeleteMapping("/deleteByName/{productName}")
	public ResponseEntity<?> deleteProduct(@PathVariable String productName) {
		try {
			String product = productService.deleteFilesByProductName(productName);

			data = product;
			code = "SUCCESS";
		} catch (AmazonServiceException e) {
			data = null;
			code = "RUNTIME_EXCEPTION";
		} catch (Exception e) {
			data = null;
			code = "EXCEPTION";
		} finally {
			return CustomeResponse.response(code, data);
		}
	}
	
	
//	@GetMapping("/downloads/{productCode}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer productCode, @RequestParam("filename") String filename) {
//        // Retrieve the Product from the database
//        Product product = productService.findProductById(productCode);
//        if (product == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Download the file content
//        byte[] fileContent = productService.downloadFile(filename);
//        if (fileContent == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        // Set the content type and disposition headers for the response
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
//
//        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//    }
//	
//======================================================================================================================================================================================-------------------------------------------------------------------------------------------

	@GetMapping("/show")
	public ResponseEntity<?> showAllProducts() {
		List<Product> productlist=productService.getallproducts();

		return new ResponseEntity<>(productlist,HttpStatus.OK);
	}
	
	@SuppressWarnings("finally")
	@GetMapping("/show2/{productCode}")
	public ResponseEntity<?> showProductByProductCode(@PathVariable Integer productCode) throws ProductNotFoundException{
		Product product=productService.findProductById(productCode);
		ProductDTO productDTO=new ProductDTO();
		try {

			productDTO = utilTransper.getProduct(product);
			data = productDTO;
			code = "SUCCESS";

		} catch (RuntimeException runtimeException) {
			data = null;
			code = "RUNTIME_EXCEPTION";
		} catch (Exception exception) {
			data = null;
			code = "EXCEPTION";
		} finally {
			return CustomeResponse.response(code, data);
		}
		
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		Product products = productService.saveproducts(product);

		return ResponseEntity.ok().body(products);
	}

	@PatchMapping("/{productCode}")
	public ResponseEntity<Product> updateProductPrice(@Valid @PathVariable Integer productCode,
			@RequestBody Product product) {
		Product updatedProduct = productService.updateProductById(productCode, product);
		return ResponseEntity.ok().body(updatedProduct);
	}

	@PutMapping("{productCode}/{quantityInStock}")
	public ResponseEntity<Product> updateProductQuantityInStock(@PathVariable Integer productCode,
			@PathVariable Integer quantityInStock) {

		Product updatedProduct = productService.updateProductQuantityInStock(productCode, quantityInStock);
		return ResponseEntity.ok().body(updatedProduct);
	}

	@PatchMapping("/{productCode}/{value}/{orderQuantity}")
	public ResponseEntity<Product> incrementdecrementProductQuantityInStock(@PathVariable Integer productCode,
			@PathVariable String value, @PathVariable Integer orderQuantity) {
		Product product = productService.incrementdecrementProductQuantityInStock(productCode, value, orderQuantity);

		return ResponseEntity.ok().body(product);
	}
	
	

}
