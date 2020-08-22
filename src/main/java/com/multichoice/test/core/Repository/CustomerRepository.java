package com.multichoice.test.core.Repository;

import org.springframework.stereotype.Repository;

import com.multichoice.test.core.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

	/**
	 * 
	 * Fetch all the data
	 * 
	 */
	public List<CustomerEntity> findAll();

	/**
	 * Find the entry by customer ID
	 */
	public Optional<CustomerEntity> findById(String id);
	
	public Optional<CustomerEntity> findByPhone(String phoneNum);

	/**
	 * Delete the entry by customer ID
	 */
	public void deleteById(String id);
	
	public String findByStatus(String id);

}