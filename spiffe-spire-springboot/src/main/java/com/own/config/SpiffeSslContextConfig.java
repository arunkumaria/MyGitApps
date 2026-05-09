
package com.own.config;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.spiffe.bundle.x509bundle.X509Bundle;
import io.spiffe.provider.WorkloadApiClientProvider;
import io.spiffe.svid.x509svid.X509Svid;
import io.spiffe.workloadapi.WorkloadApiClient;

@Configuration
public class SpiffeSslContextConfig {

	private static final String SPIRE_SOCKET = "unix:///tmp/spire-agent/public/api.sock";

	@Bean
	public SSLContext sslContext() throws Exception {

		WorkloadApiClient client = WorkloadApiClientProvider.getDefault().get(SPIRE_SOCKET);

		// 🔑 Fetch SVID
		X509Svid svid = client.fetchX509Svid();

		// 🔐 Fetch Trust Bundle
		X509Bundle bundle = client.fetchX509Bundles().getBundleForTrustDomain(svid.getSpiffeId().getTrustDomain());

		/* ---------- KeyStore (Identity) ---------- */
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		keyStore.load(null);

		X509Certificate[] certChain = svid.getCertificates().toArray(new X509Certificate[0]);
		PrivateKey privateKey = svid.getPrivateKey();

		keyStore.setKeyEntry("spiffe-key", privateKey, new char[0], certChain);

		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(keyStore, new char[0]);

		/* ---------- TrustStore (Bundle) ---------- */
		KeyStore trustStore = KeyStore.getInstance("PKCS12");
		trustStore.load(null);

		int i = 0;
		for (X509Certificate cert : bundle.getX509Authorities()) {
			trustStore.setCertificateEntry("ca-" + i++, cert);
		}

		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(trustStore);

		/* ---------- SSLContext ---------- */
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

		return sslContext;
	}
}
