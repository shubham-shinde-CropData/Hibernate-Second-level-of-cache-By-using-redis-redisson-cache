package com.cropdata.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cropdata.entity.ProductImage;
//@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

	
	Optional<ProductImage> findByProductCode(Integer productCode);
}
