package com.example.demo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	@CacheEvict(value = "city", key = "#id")
	public City getCityById(Integer id) {
		return cityRepository.findById(id).orElseThrow();
	}

	@CacheEvict(value = "city", allEntries = true)
	public City saveCity(City city) {
		return cityRepository.save(city);
	}

	// @CacheEvict(value = "city", key = "#id")
	public void deleteByCityId(Integer id) {
		cityRepository.deleteById(id);
	}

	// @CacheEvict(value = "city", key = "#city.id")
	public City update(City city) {
		return cityRepository.save(city);
	}
}
