package com.hiit.api.domain.service.it;

import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.registered.GetItRegisteredId;
import com.hiit.api.domain.usecase.it.RegisteredItEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 서비스에 등록된 IT 조회를 위한 서비스 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ItQueryService {

	private final RegisteredItDao registeredItDao;
	private final RegisteredItEntityConverter registeredItEntityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Transactional(readOnly = true)
	public List<BasicIt> execute() {
		return registeredItDao.findAll().stream()
				.map(registeredItEntityConverter::from)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public BasicIt execute(GetItId itId) {
		Optional<RegisteredItEntity> source = registeredItDao.findById(itId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetItRegisteredId.key, itId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return registeredItEntityConverter.from(source.get());
	}
}
