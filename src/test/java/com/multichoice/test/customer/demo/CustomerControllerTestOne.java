package com.multichoice.test.customer.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.multichoice.test.core.Repository.CustomerRepository;
import com.multichoice.test.core.Service.CustomerManagerImpl;
import com.multichoice.test.core.entity.CustomerEntity;



public class CustomerControllerTestOne {

	@Autowired(required = true)
	CustomerManagerImpl customerManagerImpl;

	@Test
	public void testGetCustomerDetails() {
		List<CustomerEntity> testCustomers = new ArrayList<>();
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setDob("02-07-1993");
		customerEntity.setFirst_name("saranya");
		customerEntity.setLast_name("k");
		customerEntity.setPhone("9789058776");
		customerEntity.setId("5f3e8486f7bada82ca09e4e9");
		customerEntity.setStatus(null);
		testCustomers.add(customerEntity);
		List<CustomerEntity> customer = customerManagerImpl.getCustomerDetails();
		Assert.assertSame(customer, testCustomers);
	}

	@Test
	public void testGetCustomerDetailsById() {
	}

	@Test
	public void testOnboardCustomer() {
	}

	@Test
	public void testRemoveCustomerById() {
	}

	@Test
	public void testUpdateCustomer() {
	}
}
