package com.example.util;

import java.util.function.Supplier;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.val;

public class ElasticUtil {

	public static Supplier<Query> createSupplierQuery(String approximateProductName) {
		Supplier<Query> supplier = () -> Query.of(q -> q.fuzzy(createFuzzyQuery(approximateProductName)));
		return supplier;
	}

	public static FuzzyQuery createFuzzyQuery(String approximateProductName) {
		val fuzzyQuery = new FuzzyQuery.Builder();
		return fuzzyQuery.field("name").value(approximateProductName).build();

	}
}