package com.own.springboot_10best.external;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
	private final RestTemplate rest = new RestTemplate();

	public String getWeatherEcho(String city) {
		String url = "https://postman-echo.com/get?city=" + city;
		return rest.getForObject(url, String.class);
	}
}
