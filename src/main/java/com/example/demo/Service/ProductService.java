package com.example.demo.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepo;
import com.example.util.ElasticUtil;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ElasticsearchClient elasticsearchClient;// this have search method

	public List<Product> getProducts() {
		return productRepo.findAll();
	}

	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public Product updateProduct(Product updatedProduct, String id) throws Exception {
		Product existingProduct = productRepo.findById(id)
				.orElseThrow(() -> new Exception("Product not found with id: " + id));

		existingProduct.setName(updatedProduct.getName());
		existingProduct.setDescription(updatedProduct.getDescription());
		existingProduct.setQuantity(updatedProduct.getQuantity());
		existingProduct.setPrice(updatedProduct.getPrice());

		Product savedProduct = productRepo.save(existingProduct);
		return savedProduct;
	}

	public SearchResponse<Product> fuzzySearch(String approximateProductName) throws IOException {

		// supplier query pass to search method
		Supplier<Query> supplier = ElasticUtil.createSupplierQuery(approximateProductName);

		// search method
		SearchResponse<Product> response = elasticsearchClient.search(s -> s.index("products").query(supplier.get()),
				Product.class);
		return response;
	}

	public void deleteProduct(String id) {
		productRepo.deleteById(id);
	}

}
