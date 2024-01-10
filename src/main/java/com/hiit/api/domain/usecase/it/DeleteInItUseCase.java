package com.hiit.api.domain.usecase.it;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.it.DeleteInItUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.service.it.ItRelationCommand;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
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

	private final InItDao dao;
	private final InItEntityConverter entityConverter;

	private final MemberQuery memberQuery;
	private final ItRelationCommand itRelationCommand;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

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
		dao.save(entityConverter.to(source));
		itRelationCommand.delete(source::getItRelationId);
		return AbstractResponse.VOID;
	}

	private InIt getSource(GetInItId inItId) {
		Optional<InItEntity> source = dao.findById(inItId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetInItId.key, inItId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		InItEntity inIt = source.get();
		return entityConverter.from(inIt);
	}
}
