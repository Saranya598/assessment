package com.multichoice.test.core.Controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.multichoice.test.core.Service.CustomerManagerImpl;
import com.multichoice.test.core.entity.CustomerEntity;
import com.multichoice.test.core.entity.ResponseDto;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired
	CustomerManagerImpl customerManagerImpl;

	/**
	 * Method to fetch all the customer data.
	 */
	@GetMapping
	public ResponseEntity<?> getCustomerDetails(HttpServletRequest request) {
		List<CustomerEntity> customerList = null;
		ResponseEntity<?> response = null;
		customerList = customerManagerImpl.getCustomerDetails();
		response = ResponseEntity.status(HttpStatus.ACCEPTED).body(customerList);
		return response;
	}

	/**
	 * Method to fetch all the customer data by customer ID.
	 * 
	 * @param id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getCustomerDetailsByName(@PathVariable("id") String id, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		response = customerManagerImpl.getCustomerDetailsById(id);
		return response;
	}

	/**
	 * Method to onboard customer.
	 */

	@PostMapping
	public ResponseEntity<?> onboardCustomer(@RequestBody CustomerEntity customerEntity, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		ResponseDto responseDto = new ResponseDto();
		responseDto = customerManagerImpl.onboardCustomer(customerEntity);
		if (responseDto.getStatusCode() == HttpStatus.CONFLICT.value()) {
			response = ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
		} else {
			response = ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		}
		return response;
	}

	/**
	 * Method to remove the data by id.
	 * 
	 * @param id
	 */

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> removeCustomer(@PathVariable("id") String id, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		response = customerManagerImpl.removeCustomerById(id);
		return response;
	}

	/**
	 * Method to update the data.
	 */

	@PutMapping
	public ResponseEntity<?> updateCustomer(@RequestBody CustomerEntity customerEntity, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		ResponseDto responseDto = new ResponseDto();
		responseDto = customerManagerImpl.updateCustomer(customerEntity);
		if (responseDto.getStatusCode() == HttpStatus.BAD_REQUEST.value()) {
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
		} else {
			response = ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		}
		return response;
	}
}
