package com.own.springboot_crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {

		final RestTemplate restTemplate = new RestTemplate();
		System.out.println("================q1. json string======================");
		String url = "https://git.toptal.com/screeners/invoice-json/-/raw/main/invoice.json";
		// 1. read the file
		String json = restTemplate.getForObject(url, String.class);
		System.out.println("json string:" + json);

		System.out.println("============q2. json object===========================");
		// 2. parse the string
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.readTree(json));

		List<Order> orders = mapper.readValue(json, new TypeReference<List<Order>>() {
		});

		Map<String, Long> sellerCounts = orders.stream()
				.collect(Collectors.groupingBy(o -> o.seller_name, Collectors.counting()));

		List<Map.Entry<String, Long>> sortedSellers = sellerCounts.entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue().reversed()).collect(Collectors.toList());

		System.out.println("=========q3. descending order of orders==============================");
		// 3. descending order-comparator-sellers
		sortedSellers.forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue() + " orders"));

		System.out.println("=========q4. order date format==============================");
		// 4. format - YYYY-MM-DD- order date

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (Order o : orders) {
			LocalDate date = Instant.parse(o.createdAt).atZone(ZoneId.systemDefault()).toLocalDate();
			System.out.println(o.seller_name + " | " + formatter.format(date));
		}

		System.out.println("=======q5. sales amount greater than $1000================================");
		// 5. >$1000 seller id

		Optional<Map.Entry<String, Double>> maxEntry = orders.stream().flatMap(o -> o.getOrderItems().stream())
				.filter(i -> i.getQuantity() * i.getUnitPrice() > 1000)
				.collect(Collectors.groupingBy(OrderItem::getProductCategory,
						Collectors.summingDouble(i -> i.getQuantity() * i.getUnitPrice())))
				.entrySet().stream().max(Map.Entry.comparingByValue());

		if (maxEntry.isPresent()) {
			Map.Entry<String, Double> e = maxEntry.get();
			System.out.println("Top Category: " + e.getKey() + " | Sales: $" + e.getValue());
		} else {
			System.out.println("No category meets the $1000 threshold");
		}

		System.out.println("=======q6. order id sorted in descending order================================");
		// 6. order id- descending
		List<Order> sortedByOrderIdDesc = orders.stream().sorted(Comparator.comparing(Order::getOrder_id).reversed())
				.collect(Collectors.toList());

		sortedByOrderIdDesc.forEach(System.out::println);

		System.out.println("=======q7. top 3 best product categories================================");
		// 7. top 3 best - product category

		orders.stream().filter(o -> o.orderItems != null).flatMap(o -> o.orderItems.stream())
				.filter(i -> i != null && i.getProductCategory() != null)
				.collect(Collectors.groupingBy(i -> i.getProductCategory(),
						Collectors.summingDouble(i -> i.getQuantity() * i.getUnitPrice())))
				.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
				.limit(3).forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

		System.out.println("=======q8.  avg amt spent================================");
		// 8. avg amt spent

		double avgSpent = orders.stream().flatMap(o -> o.getOrderItems().stream())
				.mapToDouble(i -> i.getQuantity() * i.getUnitPrice()).average().orElse(0);

		System.out.println("Average amount spent: $" + avgSpent);

		// 9. token based authentication

	}

}
