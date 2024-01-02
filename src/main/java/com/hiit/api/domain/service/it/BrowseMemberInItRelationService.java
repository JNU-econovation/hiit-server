package com.hiit.api.domain.service.it;

import static com.hiit.api.domain.model.it.relation.TargetItTypeInfo.REGISTERED_IT;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.support.entity.converter.in.it.InItEntityConverterImpl;
import com.hiit.api.domain.support.entity.converter.in.relation.ItRelationEntityConverterImpl;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 회원이 참여하여 생성된 IT 관계 정보를 조회하기 위한 서비스 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BrowseMemberInItRelationService {

	private final InItDao initDao;
	private final InItEntityConverterImpl inItEntityConverter;

	private final ItRelationDao itRelationDao;
	private final ItRelationEntityConverterImpl itRelationEntityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Transactional(readOnly = true)
	public List<ItRelation> browse(Long memberId) {
		log.debug("on service get member it relation : m - {}", memberId);
		List<ItRelation> source = new ArrayList<>();
		List<InIt> memberInIts = getInIts(memberId);
		for (InIt memberInIt : memberInIts) {
			ItRelation itRelation = getItRelation(memberInIt);
			if (itRelation.isType(REGISTERED_IT)) {
				source.add(itRelation);
			}
		}
		return source;
	}

	private List<InIt> getInIts(Long memberId) {
		log.debug("on service get in its : m - {}", memberId);
		return initDao.findAllActiveStatusByMember(memberId).stream()
				.map(inItEntityConverter::from)
				.collect(Collectors.toList());
	}

	private ItRelation getItRelation(InIt memberInIt) {
		log.debug("on service get it relation : ir - {}", memberInIt.getItRelationId());
		Optional<ItRelationEntity> source = itRelationDao.findById(memberInIt.getItRelationId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate("itRelationId", memberInIt.getItRelationId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return itRelationEntityConverter.from(source.get());
	}
}
