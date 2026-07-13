package com.etohfa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etohfa.dto.OrderResponseDto;
import com.etohfa.resource.OrderResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/delivery-persons")
@CrossOrigin(origins = "http://localhost:3000")
public class DeliveryPersonController {
	
	@Autowired
	private OrderResource orderResource;	
    
	@GetMapping("/{deliveryPersonId}/orders")
	@Operation(summary = "Api to fetch delivery person orders")
	public ResponseEntity<OrderResponseDto> fetchDeliveryOrders(@PathVariable("deliveryPersonId") int deliveryPersonId) {
		return orderResource.fetchDeliveryOrders(deliveryPersonId);
	}
}
