package com.hiit.api.domain.service.it;

import static com.hiit.api.domain.model.it.relation.ItTypeDetails.IT_REGISTERED;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.it.relation.GetItRelationId;
import com.hiit.api.domain.model.it.relation.It_Relation;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.support.entity.converter.in.it.InItEntityConverterImpl;
import com.hiit.api.domain.support.entity.converter.in.relation.ItRelationEntityConverterImpl;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 회원이 참여하여 생성된 IT 관계 정보를 조회하기 위한 서비스 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveItRelationBrowseService {

	private final InItDao initDao;
	private final InItEntityConverterImpl inItEntityConverter;

	private final ItRelationDao itRelationDao;
	private final ItRelationEntityConverterImpl itRelationEntityConverter;

	private final ItTimeDetailsMapper timeDetailsMapper;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Transactional(readOnly = true)
	public List<It_Relation> execute(GetMemberId memberId) {
		List<It_Relation> source = new ArrayList<>();
		List<InIt> memberInIts = getInIts(memberId.getId());
		for (InIt memberInIt : memberInIts) {
			It_Relation itRelation = getItRelation(memberInIt);
			if (itRelation.isType(IT_REGISTERED)) {
				source.add(itRelation);
			}
		}
		return source;
	}

	private List<InIt> getInIts(Long memberId) {
		List<InItEntity> entities = new ArrayList<>(initDao.findAllActiveStatusByMemberId(memberId));
		List<InIt> inIts = new ArrayList<>();
		for (InItEntity entity : entities) {
			ItRelationEntity itRelation =
					itRelationDao.findByInItIdAndStatus(entity.getId(), ItStatus.ACTIVE).orElse(null);
			assert itRelation != null;
			String info = entity.getInfo();
			InItTimeDetails timeDetails = timeDetailsMapper.read(info, InItTimeDetails.class);
			inIts.add(inItEntityConverter.from(entity, itRelation, timeDetails));
		}
		return inIts;
	}

	private It_Relation getItRelation(InIt inIt) {
		Optional<ItRelationEntity> source = itRelationDao.findById(inIt.getItRelationId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetItRelationId.key, inIt.getItRelationId());
			exceptionSource =
					logSourceGenerator.add(exceptionSource, GetMemberId.key, inIt.getMemberId());
			exceptionSource = logSourceGenerator.add(exceptionSource, GetInItId.key, inIt.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return itRelationEntityConverter.from(source.get());
	}
}
