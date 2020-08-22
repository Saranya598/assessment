package com.multichoice.test.customer.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.multichoice.test.core.Repository.CustomerRepository;
import com.multichoice.test.core.Service.CustomerManagerImpl;
import com.multichoice.test.core.entity.CustomerEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTestOne {
	private static final int NUMBER_OF_CUSTOMERS = 1;

	@Autowired(required = true)
	CustomerManagerImpl customerManagerImpl;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CacheManager cacheManager;

	@Before
	public void init() {
		flushCache();
		CustomerEntity customerEntity = new CustomerEntity();
		customerRepository.deleteAll();
		customerEntity.setDob("1994");
		customerEntity.setFirst_name("Test");
		customerEntity.setLast_name("Name");
		customerEntity.setPhone("1234567890");
		customerEntity.setStatus("true");
		customerEntity = customerRepository.save(customerEntity);
	}

	@Test
	public void testGetCustomerDetails() {
		assertEquals(NUMBER_OF_CUSTOMERS, customerManagerImpl.getCustomerDetails().size());
	}

	@Test
	public void testOnboardCustomer() {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setDob("1992");
		customerEntity.setFirst_name("Demo");
		customerEntity.setLast_name("User");
		customerEntity.setPhone("0987654321");
		customerEntity.setStatus("false");
		customerManagerImpl.onboardCustomer(customerEntity);
		assertThat(customerEntity.getId()).isNotNull();
	}

	@Test
	public void testGetCustomerDetailsById() {
		ResponseEntity<?> response = null;
		Optional<CustomerEntity> actual = customerRepository
				.findById(customerRepository.findByPhone("1234567890").get().getId());
		response = ResponseEntity.status(HttpStatus.ACCEPTED).body(actual);
		ResponseEntity<?> expected = customerManagerImpl
				.getCustomerDetailsById(customerRepository.findByPhone("1234567890").get().getId());
		assertEquals(expected.getStatusCode(), response.getStatusCode());

	}

	@Test
	public void testUpdateCustomer() {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setDob("1994");
		customerEntity.setId(customerRepository.findByPhone("1234567890").get().getId());
		customerEntity.setFirst_name("Test");
		customerEntity.setLast_name("Name");
		customerEntity.setPhone("1234567890");
		customerEntity.setStatus("false");
		customerManagerImpl.updateCustomer(customerEntity);
		assertEquals("false", customerEntity.getStatus());
	}

	@Test
	public void testRemoveCustomerById() {
		customerManagerImpl.removeCustomerById(customerRepository.findByPhone("1234567890").get().getId());
		assertEquals(0, customerManagerImpl.getCustomerDetails().size());
	}

	public void flushCache() {
		for(String cacheName : cacheManager.getCacheNames()) {
			cacheManager.getCache(cacheName).clear();
		}
	}
}
