package com.multichoice.test.core.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.multichoice.test.core.Repository.CustomerRepository;
import com.multichoice.test.core.entity.Constants;
import com.multichoice.test.core.entity.CustomerEntity;
import com.multichoice.test.core.entity.ResponseDto;

@Service
@CacheConfig(cacheNames = "customerCache")
public class CustomerManagerImpl {

	@Autowired(required = true)
	CustomerRepository customerRepository;

	@Cacheable
	public List<CustomerEntity> getCustomerDetails() {
		return customerRepository.findAll();
	}
	
	public ResponseEntity<?> getCustomerDetailsById(String id) {
		Optional<CustomerEntity> customerEntity = null;
		ResponseDto responseDto = new ResponseDto();
		ResponseEntity<?> response = null;
		customerEntity = customerRepository.findById(id);
		if (customerEntity != null) {
			String status = customerRepository.findById(id).get().getStatus();
			if (status.equalsIgnoreCase(Constants.USER_STATUS)) {
				customerEntity = customerRepository.findById(id);
				response = ResponseEntity.status(HttpStatus.ACCEPTED).body(customerEntity);
			} else {
				responseDto.setMessage(Constants.USER_DEACTIVATED);
				responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
				response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
			}
		} else {
			responseDto.setMessage(Constants.NO_ENTRY);
			responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
		}
		return response;
	}

	@CachePut(value = "onboardCustomer", key = "#customerEntity.phone")
	public ResponseDto onboardCustomer(CustomerEntity customerEntity) {
		Optional<CustomerEntity> isExist = null;
		ResponseDto responseDto = new ResponseDto();
		isExist = customerRepository.findByPhone(customerEntity.getPhone());
		if (!isExist.isPresent()) {
			customerRepository.save(customerEntity);
			responseDto.setMessage("Customer onboarded successfully");
			responseDto.setStatusCode(HttpStatus.CREATED.value());
		} else {
			responseDto.setMessage("Entry exist");
			responseDto.setStatusCode(HttpStatus.CONFLICT.value());
		}
		return responseDto;
	}
	
	@CacheEvict(value = "delete", key = "#id")
	public ResponseEntity<?> removeCustomerById(String id) {
		Optional<CustomerEntity> isExist = null;
		ResponseDto responseDto = new ResponseDto();
		ResponseEntity<?> response = null;
		isExist = customerRepository.findById(id);
		if (isExist.isPresent()) {
			customerRepository.deleteById(id);
			responseDto.setMessage("Deleted Successfully");
			responseDto.setStatusCode(HttpStatus.ACCEPTED.value());
			response = ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
		} else {
			responseDto.setMessage(Constants.NO_ENTRY);
			responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
		}
		return response;
	}
	
	@CachePut(value = "update", key = "#customerEntity.phone")
	public ResponseDto updateCustomer(CustomerEntity customerEntity) {
		ResponseDto responseDto = new ResponseDto();
		Optional<CustomerEntity> isExist = null;
		isExist = customerRepository.findById(customerEntity.getId());
		if (isExist.isPresent()) {
			customerRepository.deleteById(customerEntity.getId());
			customerRepository.save(customerEntity);
			responseDto.setMessage("Customer updated successfully");
			responseDto.setStatusCode(HttpStatus.CREATED.value());
		} else {
			responseDto.setMessage(Constants.NO_ENTRY);
			responseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
		}
		return responseDto;
	}
}
