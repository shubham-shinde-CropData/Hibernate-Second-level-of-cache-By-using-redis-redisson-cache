package com.cropdata.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	GroupedOpenApi customerApis() {
		return GroupedOpenApi.builder().group("customer").pathsToMatch("/**/customer/**").build();
	}

	@Bean
	GroupedOpenApi productApis() {
		return GroupedOpenApi.builder().group("product").pathsToMatch("/**/product/**").build();
	}

	@Bean
	GroupedOpenApi orderApis() {
		return GroupedOpenApi.builder().group("order").pathsToMatch("/**/order/**").build();
	}

	@Bean
	GroupedOpenApi orderdetailseApis() {
		return GroupedOpenApi.builder().group("orderdetailse").pathsToMatch("/**/orderdetailse/**").build();
	}
}
