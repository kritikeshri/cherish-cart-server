package com.etohfa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etohfa.dto.AddReviewRequest;
import com.etohfa.dto.CommonApiResponse;
import com.etohfa.dto.ProductReviewResponseDto;
import com.etohfa.resource.ReviewResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
	
	@Autowired
	private ReviewResource reviewResource;
	
	@PostMapping("/review")
	@Operation(summary = "Api to add product review")
	public ResponseEntity<CommonApiResponse> addProductReview(@RequestBody AddReviewRequest review) {
		return this.reviewResource.addReview(review);
	}
	
	@GetMapping("/{productId}/reviews")
	@Operation(summary = "Api to fetch product reviews")
	public ResponseEntity<ProductReviewResponseDto> fetchProductReview(@PathVariable("productId") int productId) {
		return this.reviewResource.fetchProductReviews(productId);
	}
	
	@GetMapping("seller")
	@Operation(summary = "Api to fetch seller product review")
	public ResponseEntity<ProductReviewResponseDto> fetchSellerReviews(@RequestParam("sellerId") int sellerId) {
		return this.reviewResource.fetchSellerProductReviews(sellerId);
	}

}
