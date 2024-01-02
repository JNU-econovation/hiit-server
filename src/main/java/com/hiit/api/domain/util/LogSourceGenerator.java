package com.hiit.api.domain.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LogSourceGenerator {

	public <T> Map<String, T> generate(String key, T value) {
		Map<String, T> source = new HashMap<>();
		source.put(key, value);
		return source;
	}

	public <T> Map<String, T> add(Map<String, T> source, String key, T value) {
		source.put(key, value);
		return source;
	}
}
