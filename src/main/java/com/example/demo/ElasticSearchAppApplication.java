package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@ComponentScan({ "com.example.demo.*" })
@EnableElasticsearchRepositories(basePackages = "com.example.demo.Repository")
public class ElasticSearchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchAppApplication.class, args);
	}

}
