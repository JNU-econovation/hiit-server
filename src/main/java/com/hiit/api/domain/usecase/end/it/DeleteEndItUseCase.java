package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.end.DeleteEndItUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteEndItUseCase implements AbstractUseCase<DeleteEndItUseCaseRequest> {

	private final InItDao dao;
	private final ItRelationDao itRelationDao;
	private final ItTimeDetailsMapper itTimeDetailsMapper;
	private final InItEntityConverter entityConverter;

	private final MemberQuery memberQuery;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public AbstractResponse execute(final DeleteEndItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId endInItId = request::getEndInItId;

		GetMemberId member = memberQuery.query(memberId);
		InIt source = getSource(member, endInItId);
		if (!source.isOwner(member)) {
			throw new MemberAccessDeniedException(member.getId(), endInItId.getId());
		}

		dao.deleteById(source.getId());
		return AbstractResponse.VOID;
	}

	private InIt getSource(GetMemberId memberId, GetInItId endInItId) {
		Optional<InItEntity> source =
				dao.findEndStatusByIdAndMemberId(endInItId.getId(), memberId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetMemberId.key, memberId.getId());
			exceptionSource =
					logSourceGenerator.add(exceptionSource, GetInItId.endKey, endInItId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		ItRelationEntity itRelation =
				itRelationDao.findByInItIdAndStatus(inIt.getId(), ItStatus.END).orElse(null);
		assert itRelation != null;
		String info = inIt.getInfo();
		InItTimeDetails timeInfo = itTimeDetailsMapper.read(info, InItTimeDetails.class);
		return entityConverter.from(inIt, itRelation, timeInfo);
	}
}
