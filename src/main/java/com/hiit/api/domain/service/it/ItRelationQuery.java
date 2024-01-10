package com.hiit.api.domain.service.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.relation.GetItRelationId;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.usecase.it.ItRelationEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItRelationQuery {

	private final ItRelationDao itRelationDao;
	private final ItRelationEntityConverter itRelationEntityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Transactional(readOnly = true)
	public It_Relation query(GetItRelationId itRelationId) {
		Optional<ItRelationEntity> relation = itRelationDao.findById(itRelationId.getId());
		if (relation.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetItRelationId.key, itRelationId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return itRelationEntityConverter.from(relation.get());
	}
}
