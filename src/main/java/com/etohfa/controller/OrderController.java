package com.etohfa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etohfa.dto.CommonApiResponse;
import com.etohfa.dto.OrderResponseDto;
import com.etohfa.dto.UpdateDeliveryStatusRequest;
import com.etohfa.resource.OrderResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	
	@Autowired
	private OrderResource orderResource;
	
	@PostMapping("")
	@Operation(summary = "Api to order all products from Cart")
	public ResponseEntity<CommonApiResponse> placeOrder(@RequestHeader("Authorization") String authorization) {
		return orderResource.orderProductsFromCart(authorization);
	}
	
	@GetMapping("")
	@Operation(summary = "Api to fetch all orders")
	public ResponseEntity<OrderResponseDto> fetchAllOrders() {
		return orderResource.fetchAllOrders();
	}

	@GetMapping("/{orderId}")
	@Operation(summary = "Api to fetch orders by order id")
	public ResponseEntity<OrderResponseDto> fetchOrdersByOrderId(@PathVariable("orderId") String orderId) {
		return orderResource.fetchOrdersByOrderId(orderId);
	}
	
	@PutMapping("/assign")
	@Operation(summary = "Api to assign the Delivery Person for the Order")
	public ResponseEntity<OrderResponseDto> assignDeliveryPerson(@RequestBody UpdateDeliveryStatusRequest request) {
		return orderResource.assignDeliveryPersonForOrder(request);
	}
	
	@PutMapping("/delivery-status")
	@Operation(summary = "Api to update the delivery status of Order")
	public ResponseEntity<OrderResponseDto> updateDeliveryStatus(@RequestBody UpdateDeliveryStatusRequest request) {
		return orderResource.updateDeliveryStatus(request);
	}
	
	@GetMapping("/delivery-statuses")
	@Operation(summary = "Api to fetch all delivery status")
	public ResponseEntity<List<String>> fetchAllDeliveryStatus() {
		return orderResource.fetchAllDeliveryStatus();
	}
	
	@GetMapping("/delivery-times")
	@Operation(summary = "Api to fetch all delivery timings")
	public ResponseEntity<List<String>> fetchAllDeliveryTime() {
		return orderResource.fetchAllDeliveryTime();
	}

}
