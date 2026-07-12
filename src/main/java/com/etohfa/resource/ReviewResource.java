package com.etohfa.resource;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.etohfa.dto.AddReviewRequest;
import com.etohfa.dto.CommonApiResponse;
import com.etohfa.dto.ProductReviewResponseDto;
import com.etohfa.entity.Orders;
import com.etohfa.entity.Product;
import com.etohfa.entity.Review;
import com.etohfa.entity.User;
import com.etohfa.exception.ReviewSaveFailedException;
import com.etohfa.service.OrderService;
import com.etohfa.service.ProductService;
import com.etohfa.service.ReviewService;
import com.etohfa.service.UserService;
import com.etohfa.utility.Constants.DeliveryStatus;

@Component
public class ReviewResource {

	private final Logger LOG = LoggerFactory.getLogger(ProductResource.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	public ResponseEntity<CommonApiResponse> addReview(AddReviewRequest request) {

		LOG.info("request received for adding product review");

		CommonApiResponse response = new CommonApiResponse();

		if (request == null || request.getUserId() == 0 || request.getProductId() == 0 || request.getStar() == 0
				|| request.getReview() == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(request.getUserId());

		if (user == null) {
			response.setResponseMessage("user not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Product product = this.productService.getProductById(request.getProductId());

		if (product == null) {
			response.setResponseMessage("product not found");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		// Check if the user has any delivered orders for the product
		List<Orders> orders = new ArrayList<>();
		orders = orderService.getOrdersByUserAndProductAndStatus(request.getUserId(), request.getProductId(),
			Arrays.asList(DeliveryStatus.DELIVERED.value()));
			
		if (CollectionUtils.isEmpty(orders)) {
			response.setResponseMessage("No Delivered Orders Found for the user and product");
			response.setSuccess(false);
			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		} else {
			Review review = new Review();
			review.setProduct(product);
			review.setReview(request.getReview());
			review.setStar(request.getStar());
			review.setUser(user);

			Review addedReview = this.reviewService.addReview(review);
			if (addedReview == null) {
				throw new ReviewSaveFailedException("Failed to save the review");
			}
			response.setResponseMessage("product review added successful");
			response.setSuccess(true);
		}
		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<ProductReviewResponseDto> fetchProductReviews(int productId) {

		LOG.info("request received for fetching the product reviews");

		ProductReviewResponseDto response = new ProductReviewResponseDto();

		if (productId == 0) {
			response.setResponseMessage("product id missing");
			response.setSuccess(false);

			return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Product product = this.productService.getProductById(productId);

		if (product == null) {
			response.setResponseMessage("product not found");
			response.setSuccess(false);

			return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Review> reviews = this.reviewService.fetchProductReviews(Arrays.asList(product));

		if (CollectionUtils.isEmpty(reviews)) {
			response.setResponseMessage("No product reviews yet");
			response.setSuccess(false);

			return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.OK);
		}
		
		double averageRating = averageProductRating(reviews);

		response.setReviews(reviews);
		response.setAverageRating(averageRating);
		response.setResponseMessage("product reviews fetched");
		response.setSuccess(true);

		return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.OK);
	}

	private double averageProductRating(List<Review> reviews) {

		int totalReviews = reviews.size();

		if (totalReviews == 0) {
			return 0.0;
		}

		// Calculate the sum of all the ratings
		int sum = 0;

		for (Review review : reviews) {
			sum += review.getStar();
		}

		// Calculate the average rating
		double averageRating = (double) sum / totalReviews;
		
		// Format the average rating to one decimal place
	    DecimalFormat df = new DecimalFormat("#.#");
	    averageRating = Double.parseDouble(df.format(averageRating));

		return averageRating;
	}

	public ResponseEntity<ProductReviewResponseDto> fetchSellerProductReviews(int sellerId) {

		LOG.info("request received for fetching the seller product reviews");

		ProductReviewResponseDto response = new ProductReviewResponseDto();

		if (sellerId == 0) {
			response.setResponseMessage("seller id missing");
			response.setSuccess(false);

			return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		User seller = this.userService.getUserById(sellerId);

		if (seller == null) {
			response.setResponseMessage("seller not found");
			response.setSuccess(false);

			return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Review> reviews = this.reviewService.fetchSellerProductReview(seller);

		if (CollectionUtils.isEmpty(reviews)) {
			response.setResponseMessage("No product reviews yet");
			response.setSuccess(false);

			return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		response.setReviews(reviews);
		response.setResponseMessage("product reviews fetched");
		response.setSuccess(true);

		return new ResponseEntity<ProductReviewResponseDto>(response, HttpStatus.OK);
	}

}
