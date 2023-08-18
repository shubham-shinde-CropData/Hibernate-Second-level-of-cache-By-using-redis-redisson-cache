package com.example.demo.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
//@EnableJpaRepositories(basePackages = "com.devtalkers.hibernate2levelcache.repository")

public interface CityRepository extends JpaRepository<City, Integer> {

	
}