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
import org.springframework.web.bind.annotation.RestController;

import com.etohfa.dto.CategoryResponseDto;
import com.etohfa.dto.CommonApiResponse;
import com.etohfa.entity.Category;
import com.etohfa.resource.CategoryResource;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {
	
	@Autowired
	private CategoryResource categoryResource;
	
	@PostMapping("")
	@Operation(summary = "Api to add category")
	public ResponseEntity<CommonApiResponse> addCategory(@RequestBody Category category) {
		return categoryResource.addCategory(category);
	}
	
	@PutMapping("/{categoryId}")
	@Operation(summary = "Api to update category")
	public ResponseEntity<CommonApiResponse> updateCategory(@RequestBody Category category, @PathVariable("categoryId") int categoryId) {
		return categoryResource.updateCategory(category);
	}
	
	@GetMapping("")
	@Operation(summary = "Api to fetch all category")
	public ResponseEntity<CategoryResponseDto> fetchAllCategory() {
		return categoryResource.fetchAllCategory();
	}
	
	@DeleteMapping("/{categoryId}")
	@Operation(summary = "Api to delete category all its products")
	public ResponseEntity<CommonApiResponse> deleteCategory(@PathVariable("categoryId") int categoryId) {
		return categoryResource.deleteCategory(categoryId);
	}

}
