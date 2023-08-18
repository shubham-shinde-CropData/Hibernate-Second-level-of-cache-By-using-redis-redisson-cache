package com.cropdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.connect.AmazonConnect;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {

	@Value("${Cloud.aws.credential.accessKey}")
	private String accessKey;

	@Value("${Cloud.aws.credential.secretKey}")
	private String secretKey;

	@Value("${Cloud.aws.region.static}")
	private String region;

	@Bean
	public AmazonS3 generateAmazonS3Client() {
    	AWSCredentials  credentials=new BasicAWSCredentials(accessKey, secretKey);
    	//with this creadential we can build amazon s3 object
    return	AmazonS3ClientBuilder
    		.standard()
    		.withCredentials(new AWSStaticCredentialsProvider(credentials))
    		.withRegion(region).build();
    	
    }

}
