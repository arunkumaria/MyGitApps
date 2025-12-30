
package com.own.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;

@Service
public class SpiffeClient {

	private final SSLContext sslContext;

	public SpiffeClient(SSLContext sslContext) {
		this.sslContext = sslContext;
	}

	public String callServer() throws Exception {

		try (CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build()) {

			HttpGet request = new HttpGet("https://localhost:8443/hello");

			return client.execute(request, response -> new String(response.getEntity().getContent().readAllBytes()));
		}
	}
}
