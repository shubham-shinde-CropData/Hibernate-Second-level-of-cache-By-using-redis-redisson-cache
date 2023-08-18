package com.cropdata.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.amazonaws.services.s3.model.S3Object;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.cropdata.entity.Order;
import com.cropdata.entity.OrderDetails;
import com.cropdata.entity.Product;
import com.cropdata.entity.ProductImage;
import com.cropdata.exception.ProductNotFoundException;
import com.cropdata.repo.ProductRepository;
import com.cropdata.service.IProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private AmazonS3 s3client;// by using we can directly upload delete upate the file

	@Autowired
	private AWSS3Service awsS3Service;

	@Value("${application.bucket.name}")
	private String buckateName;

	// upload file ----------------------

	public String uploadFile(Product product, MultipartFile file) {
		Product savedProduct = productRepository.save(product);

		File fileObj = convertMultipartFileToFile(file);
		String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		s3client.putObject(new PutObjectRequest(buckateName, filename, fileObj));
		awsS3Service.uploadFile(savedProduct.getProductCode().toString(), file);
		fileObj.delete();
		return "file uploaded : " + filename;
	}

	// download file ----------------
	public byte[] downloadFile(String filename) {
		com.amazonaws.services.s3.model.S3Object s3Object = s3client.getObject(buckateName, filename);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
//	public byte[] downloadFile(String filename) {
//	    try {
//	        S3Object s3Object = s3client.getObject(buckateName, filename);
//	        S3ObjectInputStream inputStream = s3Object.getObjectContent();
//	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//	        byte[] buffer = new byte[4096];
//	        int bytesRead;
//	        while ((bytesRead = inputStream.read(buffer)) != -1) {
//	            outputStream.write(buffer, 0, bytesRead);
//	        }
//	        byte[] content = outputStream.toByteArray();
//	        inputStream.close();
//	        outputStream.close();
//	        return content;
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    return null;
//	}

	public String deleteFile(String filename) {
		s3client.deleteObject(buckateName, filename);

		return filename + " removed......";

	}

	@Override
	public String deleteFilesByProductName(String productName) {
		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(buckateName);
		ListObjectsV2Result result;
		int count = 0;
		result = s3client.listObjectsV2(request);

		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
			String key = objectSummary.getKey();
			ObjectMetadata metadata = s3client.getObjectMetadata(buckateName, key);
			Map<String, String> userMetadata = metadata.getUserMetadata();

			if (userMetadata != null && userMetadata.containsKey("productName")
					&& userMetadata.get("productName").equals(productName)) {
				s3client.deleteObject(buckateName, key);
				count++;
			}
		}
		if (count == 0) {
			System.out.println("product not delete");
		}

		return "Deleted  product image(s) successfully.";
	}

	@Override
	public String deleteFilesByProductCode(Integer productCode) {

		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(buckateName);
		ListObjectsV2Result result;
		int count = 0;
		result = s3client.listObjectsV2(request);

		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
			String key = objectSummary.getKey();
			ObjectMetadata metadata = s3client.getObjectMetadata(buckateName, key);
			Map<String, String> userMetadata = metadata.getUserMetadata();

			if (userMetadata != null && userMetadata.containsKey("productCode")
					&& userMetadata.get("productCode").equals(productCode.toString())) {
				s3client.deleteObject(buckateName, key);
				count++;
			}

		}
		if (count == 0) {
			System.out.println("product not delete");
		}

		return "Deleted  product images successfully.";
	}

	private File convertMultipartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
			fos.write(file.getBytes());

		} catch (IOException e) {
			log.error("error converting multipartfile to file ", e);
		}
		return convertedFile;

	}

// -----------------------------------------AWS End------------------------------------------------------------------------------

	@Override
	//@Cacheable(value = " product")
	public List<Product> getallproducts() {
		List<Product> listOfAllProducts = productRepository.findAll();
		return listOfAllProducts;
	}

	@Override
	public Product saveproducts(Product product) {
		productRepository.save(product);
		return null;
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "product", allEntries = true) })
	public Product createProduct(Product product) {
		try {

			MultipartFile file = product.getFile();
			if (!file.getContentType().equals("application/pdf")) {
				throw new IllegalArgumentException("Only PDF files are allowed.");
			}

			String fileName = product.getProductName() + ".pdf";
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());

			PutObjectRequest putObjectRequest = new PutObjectRequest(buckateName, fileName, file.getInputStream(),
					metadata);
			s3client.putObject(putObjectRequest);

			ProductImage productImage = new ProductImage();
			productImage.setFileName(fileName);
			productImage.setFileSize(file.getSize());
			productImage.setFileType(file.getContentType());

		} catch (Exception e) {
			throw new IllegalArgumentException("Product Not Created");

		}
		return productRepository.save(product);
	}

	@Override
	@Cacheable(value = "product",key="#productCode")
	public Product findProductById(Integer productCode) {
		Product findproduct = productRepository.findById(productCode).get();
		System.out.println("-----------------------");
		System.out.println(productCode.longValue());
		try {
			if (findproduct.getProductCode() != null) {
				productRepository.findById(productCode).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findproduct;
	}

	@Override
	@CachePut(value = "product", key = "#productCode")
	public Product updateProduct(Product product) {

		Product updatedProduct = productRepository.findById(product.getProductCode()).get();
		try {
			if (product.getProductCode() != null) {
				productRepository.save(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedProduct;
	}

	@Override
	@CacheEvict(value = "product", key = "#productCode")
	public String deleteProduct(Integer productCode) {

		Product deletedProduct = productRepository.findById(productCode).get();
		try {
			if (deletedProduct.getProductCode() != null)
				productRepository.delete(deletedProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Entry Deleted succesfully";
	}

	@Override
	@CachePut(value = "product", key = "#productCode")
	public Product incrementdecrementProductQuantityInStock(Integer productCode, String value, Integer orderQuantity) {
		Product existingProduct = productRepository.findById(productCode).get();

		if (existingProduct != null && (value.equalsIgnoreCase("increment")) && (orderQuantity >= 1)) {
			Integer increasedQuantityInStock = existingProduct.getQuantityInStock() + orderQuantity;
			existingProduct.setQuantityInStock(increasedQuantityInStock);
			return productRepository.save(existingProduct);

		} else if ((existingProduct != null) && (value.equalsIgnoreCase("decrement"))) {
			Integer originalQuantityInStock = existingProduct.getQuantityInStock();

			if (originalQuantityInStock >= orderQuantity) {
				Integer decreaseQuantityInStock = originalQuantityInStock - orderQuantity;
				existingProduct.setQuantityInStock(decreaseQuantityInStock);
				return productRepository.save(existingProduct);

			}

		}
		// throw new ProductNotFoundException("Product Not Found");
		System.out.println("Product Not Found");
		return existingProduct;
	}

	@Override
	public Product uploadImages(Product product) throws IOException {

		List<MultipartFile> files = product.getFiles();
		Integer productCode = product.getProductCode();

		Product product2 = findProductById(productCode);

		int i = 1;
		for (MultipartFile file : files) {
			String fileName = product2.getProductName() + "/" + product2.getProductName()
					+ product2.getProductDescription() + "_" + i;
			i++;
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.addUserMetadata("productCode", String.valueOf(productCode));
			metadata.addUserMetadata("productName", product2.getProductName());
			metadata.setContentLength(file.getSize());

			PutObjectRequest putObjectRequest = new PutObjectRequest(buckateName, fileName, file.getInputStream(),
					metadata);
			s3client.putObject(putObjectRequest);

		}
		return productRepository.save(product);
	}

	@Override
	@Cacheable(value = "product", key = "#productCode")
	public Product updateProductQuantityInStock(Integer productCode, Integer quantityOrdered) {
		Product existingProduct = productRepository.findById(productCode).get();
		Integer originalQuantityInStock = existingProduct.getQuantityInStock();
		try {

			Integer updatedProductQuantity = existingProduct.getQuantityInStock() + quantityOrdered;

			existingProduct.setQuantityInStock(updatedProductQuantity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return productRepository.save(existingProduct);

	}

	@Override
	@Caching(evict = { @CacheEvict(value = "product", allEntries = true) }, put = {
			@CachePut(value = "product", key = "#productCode") })
	public Product updateProductById(Integer productCode, Product product) {
		Product updateProduct = productRepository.findById(productCode).get();

		Product productSaved = null;
		try {
			if (updateProduct.getProductCode() != null) {
				System.out.println(updateProduct.getProductDescription());
				System.out.println(updateProduct.getProductName());
				updateProduct.setPrice(product.getPrice());
				updateProduct.setQuantityInStock(product.getQuantityInStock());
				productSaved = productRepository.save(updateProduct);

			} else {
				System.out.println("price cannot be null");
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return productSaved;

	}

}
