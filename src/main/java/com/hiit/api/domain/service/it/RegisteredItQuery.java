package com.hiit.api.domain.service.it;

import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
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
public class RegisteredItQuery implements ItQuery {

	private final RegisteredItDao registeredItDao;
	private final RegisteredItEntityConverter registeredItEntityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public BasicIt query(ItTypeDetails type, GetItId itId) {
		Optional<RegisteredItEntity> source = registeredItDao.findById(itId.getId());
		if (source.isEmpty()) {
			Map<String, String> exceptionSource =
					logSourceGenerator.generate(GetItId.key, itId.toString());
			exceptionSource = logSourceGenerator.add(exceptionSource, "it_type", type.getValue());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return registeredItEntityConverter.from(source.get());
	}

	@Override
	public ItTypeDetails getType() {
		return ItTypeDetails.IT_REGISTERED;
	}
}
