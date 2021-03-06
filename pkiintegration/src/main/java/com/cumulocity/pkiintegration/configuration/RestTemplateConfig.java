package com.cumulocity.pkiintegration.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateConfig {

	// TODO: Move to tenant options
	private static final String AUTHENTICATION_CERTIFICATE_PATH = "classpath:certificate/so38.2.p12";
	// TODO: Move to tenant options
	private static final String CERTIFICATE_PASSWORD = "FU63C84I";
	
	@Autowired
	private ResourceLoader resourceLoader;

	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException, UnrecoverableKeyException, KeyManagementException {
		log.info("Initializing RestTemplate to connect with Certificate-Authority API");
		KeyStore clientStore = KeyStore.getInstance("PKCS12");
		InputStream inputStream = resourceLoader.getResource(AUTHENTICATION_CERTIFICATE_PATH).getInputStream();
		clientStore.load(inputStream, CERTIFICATE_PASSWORD.toCharArray());
		printCertificates(CERTIFICATE_PASSWORD, clientStore);
		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(clientStore, CERTIFICATE_PASSWORD.toCharArray())
				.loadTrustMaterial(clientStore, new TrustSelfSignedStrategy() {
					@Override
					public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
							throws java.security.cert.CertificateException {
						return true;
					}
				}).build();

		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(factory);
	}

	// This method is for debugging only, to print the keys contained in a
	// certificate.
	// TODO: can be deleted later.
	private void printCertificates(String certificatePassword, KeyStore clientStore)
			throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
		Enumeration<?> aliasEnum = clientStore.aliases();

		Key key = null;
		Certificate cert = null;

		while (aliasEnum.hasMoreElements()) {
			String keyName = (String) aliasEnum.nextElement();
			key = clientStore.getKey(keyName, certificatePassword.toCharArray());
			cert = clientStore.getCertificate(keyName);
		}

		KeyPair kp = new KeyPair(cert.getPublicKey(), (PrivateKey) key);
		System.out.println(kp.getPrivate());
		System.out.println(kp.getPublic());
	}
}
