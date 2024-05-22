package com.example.demo.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Product;
import com.example.demo.Service.ProductService;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/findAll")
	List<Product> findAll() {
		return productService.getProducts();

	}

	@PostMapping("/add")
	public Product insertProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}

	@GetMapping("/fuzzySearch/{approximateProductName}")
	List<Product> fuzzySearch(@PathVariable String approximateProductName) throws IOException {

		SearchResponse<Product> searchResponse = productService.fuzzySearch(approximateProductName);
		List<Hit<Product>> hitList = searchResponse.hits().hits();

		List<Product> productList = new ArrayList<>();
		for (Hit<Product> hit : hitList) {
			productList.add(hit.source());
			System.out.println(hit.source());
		}
		return productList;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) {

		try {
			productService.deleteProduct(id);
			return new ResponseEntity<>("deleted successfully ", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.OK);

		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Product updatedProduct, @PathVariable String id)
			throws Exception {

		try {
			productService.updateProduct(updatedProduct, id);
			return new ResponseEntity<>("updated successfully ", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.OK);

		}

	}

}
