package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Product;

@Service
public interface ProductRepo extends ElasticsearchRepository<Product, String> {
	@Override
	List<Product> findAll();
}
