//package com.own.service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.own.model.OrderRequest;
//
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//
//@Service
//public class BffService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @CircuitBreaker(
//            name = "foodService",
//            fallbackMethod = "foodFallback"
//    )
//    public Map<String, Object> dashboard() {
//
//        ResponseEntity<List<Map<String, Object>>> foodsResponse =
//                restTemplate.exchange(
//                        "http://FOOD-SERVICE/foods",
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<Map<String, Object>>>() {}
//                );
//
//        ResponseEntity<List<Map<String, Object>>> restaurantResponse =
//                restTemplate.exchange(
//                        "http://RESTAURANT-SERVICE/restaurants",
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<List<Map<String, Object>>>() {}
//                );
//
//        Map<String, Object> response = new HashMap<>();
//
//        response.put("foods", foodsResponse.getBody());
//        response.put("restaurants", restaurantResponse.getBody());
//
//        return response;
//    }
//
//    public Map<String, Object> foodFallback(Exception ex) {
//
//        Map<String, Object> response = new HashMap<>();
//
//        response.put("message", "Food Service Temporarily Down");
//
//        return response;
//    }
//
//    @CircuitBreaker(
//            name = "orderService",
//            fallbackMethod = "orderFallback"
//    )
//    public String placeOrder(OrderRequest request) {
//
//        HttpHeaders headers = new HttpHeaders();
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<OrderRequest> entity =
//                new HttpEntity<>(request, headers);
//
//        ResponseEntity<String> response =
//                restTemplate.exchange(
//                        "http://ORDER-SERVICE/orders",
//                        HttpMethod.POST,
//                        entity,
//                        String.class
//                );
//
//        return response.getBody();
//    }
//
//    public String orderFallback(
//            OrderRequest request,
//            Exception ex
//    ) {
//
//        return "Order Service Temporarily Unavailable";
//    }
//}