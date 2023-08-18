package com.cropdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import com.cropdata.enums.OrderStatusEnum;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@EntityScan(basePackages = { "com.cropdata.entity", "com.amazonaws.services.codecommit.model" })
@EnableCaching
public class SpringBootSampleProjectSetupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSampleProjectSetupApplication.class, args);

		String statusString = "CONFIRMED";
		OrderStatusEnum statusEnum = OrderStatusEnum.valueOf(statusString);
		System.out.println(statusEnum);
		System.out.println("----------------Wellcome--------------------");
	}

}
