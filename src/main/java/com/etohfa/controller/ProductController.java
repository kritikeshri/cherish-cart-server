package com.etohfa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.etohfa.dto.CommonApiResponse;
import com.etohfa.dto.ProductAddRequest;
import com.etohfa.dto.ProductDetailUpdateRequest;
import com.etohfa.dto.ProductResponseDto;
import com.etohfa.resource.ProductResource;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/products")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://kritikeshri.github.io"
})
public class ProductController {

	@Autowired
	private ProductResource productResource;

	@PostMapping("")
	@Operation(summary = "Api to add product")
	public ResponseEntity<CommonApiResponse> addProduct(ProductAddRequest productDto) {
		return this.productResource.addProduct(productDto);
	}

	@PutMapping("/{productId}")
	@Operation(summary = "Api to update product details excluding image")
	public ResponseEntity<CommonApiResponse> updateProductDetails(
		@RequestBody ProductDetailUpdateRequest request, @PathVariable("productId") int productId) {
		return this.productResource.updateProductDetail(request, productId);
	}

	@PutMapping("/images")
	@Operation(summary = "Api to update product images")
	public ResponseEntity<CommonApiResponse> updateProductImage(ProductAddRequest request) {
		return this.productResource.updateProductImage(request);
	}

	@DeleteMapping("/{productId}")
	@Operation(summary = "Api to delete product")
	public ResponseEntity<CommonApiResponse> deleteProduct(@PathVariable("productId") int productId,
			@RequestHeader("Authorization") String authorization) {
		return this.productResource.deleteProduct(productId, authorization);
	}

	@GetMapping("/{productId}")
	@Operation(summary = "Api to fetch product by Id")
	public ResponseEntity<ProductResponseDto> fetchProductById(@PathVariable("productId") Integer productId) {
		return this.productResource.fetchProductById(productId);
	}

	@GetMapping("")
	@Operation(summary = "Api to fetch all active product")
	public ResponseEntity<ProductResponseDto> fetchAllProduct() {
		return this.productResource.fetchAllProducts();
	}

	@GetMapping("search/{productName}")
	@Operation(summary = "Api to search the products by name")
	public ResponseEntity<ProductResponseDto> searchProductsByName(
			@PathVariable(name = "productName") String productName) {
		return this.productResource.searchProductByName(productName);
	}

	@GetMapping(value = "/images/{productImageName}", produces = "image/*")
	public void getProductImage(@PathVariable("productImageName") String productImageName, HttpServletResponse resp) {
		this.productResource.getProductImage(productImageName, resp);
	}
}	
