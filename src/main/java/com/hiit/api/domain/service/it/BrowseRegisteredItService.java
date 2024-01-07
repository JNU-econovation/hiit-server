package com.hiit.api.domain.service.it;

import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.usecase.it.RegisteredItEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrowseRegisteredItService implements BrowseTargetItService {

	private final RegisteredItDao registeredItDao;
	private final RegisteredItEntityConverter registeredItEntityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public BasicIt browse(TargetItTypeInfo type, Long itId) {
		Optional<RegisteredItEntity> source = registeredItDao.findById(itId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("itId", itId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return registeredItEntityConverter.from(source.get());
	}

	@Override
	public TargetItTypeInfo getType() {
		return TargetItTypeInfo.REGISTERED_IT;
	}
}
