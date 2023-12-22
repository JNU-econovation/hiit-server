package com.hiit.api.domain.service.end.it;

import com.hiit.api.domain.dao.it.relation.TargetItTypeInfo;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItTimeInfoExecutorManager {

	private final Map<String, ItTimeInfoExecutor> executors;

	public ItTimeInfoExecutor getExecutor(TargetItTypeInfo targetType) {
		return this.getExecutor(targetType.getType());
	}

	public ItTimeInfoExecutor getExecutor(String targetType) {
		String executorKey =
				executors.keySet().stream()
						.filter(key -> key.toLowerCase().contains(targetType.toLowerCase()))
						.findAny()
						.orElseThrow(
								() ->
										new IllegalArgumentException(
												"not exist executor for targetType : " + targetType));
		return executors.get(executorKey);
	}
}
