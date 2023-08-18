package com.cropdata.serviceimpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AWSS3Service {

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${application.bucket.name}")
	private String buckateName;

	public void uploadFile(String filename, MultipartFile file) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());

			PutObjectRequest request = new PutObjectRequest(buckateName, filename, file.getInputStream(), metadata);
			amazonS3.putObject(request);
		} catch (IOException e) {
			System.out.println("exeption");
		}
	}
}
