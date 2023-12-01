package com.hiit.api.notification.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.hiit.api.notification.porperty.FirebaseProperty;
import java.io.IOException;
import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

	@Bean
	public FirebaseProperty fireBaseProperty() {
		return new FirebaseProperty();
	}

	@Bean
	public FirebaseApp firebaseApp(FirebaseProperty firebaseProperty) throws IOException {
		FirebaseOptions options =
				FirebaseOptions.builder()
						.setCredentials(
								ServiceAccountCredentials.newBuilder()
										.setProjectId(firebaseProperty.getProjectId())
										.setPrivateKeyId(firebaseProperty.getPrivateKeyId())
										.setPrivateKeyString(firebaseProperty.getPrivateKey())
										.setClientEmail(firebaseProperty.getClientEmail())
										.setClientId(firebaseProperty.getClientId())
										.setTokenServerUri(URI.create(firebaseProperty.getTokenUri()))
										.build())
						.build();

		return FirebaseApp.initializeApp(options);
	}
}
