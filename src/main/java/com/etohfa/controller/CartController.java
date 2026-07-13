package com.etohfa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etohfa.dto.CartRequestDto;
import com.etohfa.dto.CartResponseDto;
import com.etohfa.dto.CommonApiResponse;
import com.etohfa.resource.CartResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/carts")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

	@Autowired
	private CartResource cartResource;

	@PostMapping("/items")
	@Operation(summary = "Api to add cart")
	public ResponseEntity<CommonApiResponse> addCategory(@RequestBody CartRequestDto request, @RequestHeader("Authorization") String authorization) {
		return cartResource.addToCart(request, authorization);
	}
	
	@PutMapping("/items")
	@Operation(summary = "Api to update cart")
	public ResponseEntity<CartResponseDto> updateCart(@RequestBody CartRequestDto request) {
		return cartResource.updateCart(request);
	}
	
	@DeleteMapping("/items")
	@Operation(summary = "Api to delete cart")
	public ResponseEntity<CartResponseDto> deleteCart(@RequestBody CartRequestDto request) {
		return cartResource.deleteCart(request);
	}
	
	@GetMapping(value = "/items", params = "userId")
	@Operation(summary = "Api to get the cart items for user")
	public ResponseEntity<CartResponseDto> getCartItemsByUserId(@RequestParam("userId") int userId) {
		return cartResource.getCartItemsByUserIdDetails(userId);
	}

}
