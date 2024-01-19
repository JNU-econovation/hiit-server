package com.hiit.api.domain.usecase.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.it.DeleteInItUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.ItTimeDetailsMapper;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.event.DeleteInItEventPublisher;
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
public class DeleteInItUseCase implements AbstractUseCase<DeleteInItUseCaseRequest> {

	private final InItDao itDao;
	private final ItRelationDao itRelationDao;
	private final ItTimeDetailsMapper itTimeDetailsMapper;
	private final InItEntityConverter itEntityConverter;

	private final MemberQuery memberQuery;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	private final DeleteInItEventPublisher publisher;

	@Override
	@Transactional
	public AbstractResponse execute(DeleteInItUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId inItId = request::getInItId;
		final String endTitle = request.getEndTitle();

		GetMemberId member = memberQuery.query(memberId);
		InIt source = getSource(inItId);
		if (!source.isOwner(member)) {
			throw new MemberAccessDeniedException(member.getId(), inItId.getId());
		}

		source.updateTitle(endTitle);
		source.end();

		end(source);
		publisher.publish(inItId.getId(), member.getId());
		return AbstractResponse.VOID;
	}

	private InIt getSource(GetInItId inItId) {
		Optional<InItEntity> source = itDao.findById(inItId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.key, inItId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		ItRelationEntity itRelation =
				itRelationDao.findByInItIdAndStatus(inIt.getId(), ItStatus.ACTIVE).orElse(null);
		assert itRelation != null;
		String info = inIt.getInfo();
		InItTimeDetails timeDetails = itTimeDetailsMapper.read(info, InItTimeDetails.class);
		return itEntityConverter.from(inIt, itRelation, timeDetails);
	}

	private void end(InIt source) {
		itDao.endById(source.getId(), source.getTitle());
		itRelationDao.endById(source.getItRelationId());
	}
}
