package com.hiit.api.client.service;

import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "foo-client", url = "send to parameter URI")
public interface FooFeignClient {

	/** Health Check 기능 */
	@GetMapping
	ResponseEntity<String> healthCheck(URI uri);
}
