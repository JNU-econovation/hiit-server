package com.hiit.api.domain.service.end.it;

import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.dao.it.registerd.RegisteredItData;
import com.hiit.api.domain.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisteredItTimeInfoExecutor implements ItTimeInfoExecutor {

	private final RegisteredItDao registeredItDao;

	@Override
	public ItTimeInfo execute(Long id) {
		RegisteredItData registeredIt = getRegisteredIt(id);
		return ItTimeInfo.builder()
				.startTime(registeredIt.getStartTime())
				.endTime(registeredIt.getEndTime())
				.build();
	}

	private RegisteredItData getRegisteredIt(Long registeredItId) {
		return registeredItDao
				.findById(registeredItId)
				.orElseThrow(() -> new DataNotFoundException("registeredIt : " + registeredItId));
	}
}
