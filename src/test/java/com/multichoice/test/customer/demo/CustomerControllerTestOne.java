package com.multichoice.test.customer.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.multichoice.test.core.Repository.CustomerRepository;
import com.multichoice.test.core.Service.CustomerManagerImpl;
import com.multichoice.test.core.entity.CustomerEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTestOne {

	@Autowired(required = true)
	CustomerManagerImpl customerManagerImpl;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CacheManager cacheManager;

	@Test
	public void testGetCustomerDetails() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String base64Encoded = "Basic" + " "
				+ (Base64.getEncoder().encodeToString(("technical" + ":" + "Assessment").getBytes()));
		headers.add("Authorization", base64Encoded);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:" + 8888 + "/customer";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		// Verify request succeed
		Assert.assertEquals(202, response.getStatusCodeValue());
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testCreateCustomerList() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		CustomerEntity customerEntity = new CustomerEntity();
		getDetails(customerEntity);
		HttpHeaders headers = new HttpHeaders();
		String base64Encoded = "Basic" + " "
				+ (Base64.getEncoder().encodeToString(("technical" + ":" + "Assessment").getBytes()));
		headers.add("Authorization", base64Encoded);
		HttpEntity<Object> request = new HttpEntity<Object>(customerEntity, headers);
		final String baseUrl = "http://localhost:" + 8888 + "/customer";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
		// Verify request succeed
		Assert.assertEquals(201, response.getStatusCodeValue());
	}
	
	private CustomerEntity getDetails (CustomerEntity customerEntity) {
		customerEntity.setDob("1992");
		customerEntity.setFirst_name("Demo");
		customerEntity.setLast_name("User");
		customerEntity.setPhone("0987654321");
		customerEntity.setStatus("true");
		return customerEntity;
	}

	@Test
	public void testGetCustomerDetailsById() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		CustomerEntity customerEntity = new CustomerEntity();
		formDetails(customerEntity);
		String id = customerRepository.findByPhone("12345678").get().getId();
		HttpHeaders headers = new HttpHeaders();
		String base64Encoded = "Basic" + " "
				+ (Base64.getEncoder().encodeToString(("technical" + ":" + "Assessment").getBytes()));
		headers.add("Authorization", base64Encoded);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:" + 8888 + "/customer/" + id;
		URI uri = new URI(baseUrl);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
		// Verify request succeed
		Assert.assertEquals(202, response.getStatusCodeValue());
	}
	
	private void formDetails (CustomerEntity customerEntity) {
		customerEntity.setDob("1992");
		customerEntity.setFirst_name("DemoOne");
		customerEntity.setLast_name("UserOne");
		customerEntity.setPhone("12345678");
		customerEntity.setStatus("true");
		customerRepository.save(customerEntity);
	}
	
	@Test
	public void testRemoveCustomerById() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String id = customerRepository.findByPhone("12345678").get().getId();
		HttpHeaders headers = new HttpHeaders();
		String base64Encoded = "Basic" + " "
				+ (Base64.getEncoder().encodeToString(("technical" + ":" + "Assessment").getBytes()));
		headers.add("Authorization", base64Encoded);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:" + 8888 + "/customer/" + id;
		URI uri = new URI(baseUrl);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);
		// Verify request succeed
		Assert.assertEquals(202, response.getStatusCodeValue());
		Assert.assertEquals(true, response.getBody().contains("Deleted"));
	}

}