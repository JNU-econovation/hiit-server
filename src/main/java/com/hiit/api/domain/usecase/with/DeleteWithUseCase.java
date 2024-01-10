package com.hiit.api.domain.usecase.with;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.with.DeleteWithUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.exception.TimePolicyException;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.with.GetWithId;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeDetails;
import com.hiit.api.domain.service.it.in.ActiveInItTimeDetailsQueryImpl;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.with.WithEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteWithUseCase implements AbstractUseCase<DeleteWithUseCaseRequest> {

	private final WithDao dao;
	private final WithEntityConverterImpl entityConverter;

	private final MemberQuery memberQuery;

	private final ActiveInItTimeDetailsQueryImpl activeInItTimeDetailsQuery;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public AbstractResponse execute(final DeleteWithUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetWithId withId = request::getWithId;

		GetMemberId member = memberQuery.query(memberId);
		With source = getSource(withId);
		if (source.isOwner(member)) {
			throw new MemberAccessDeniedException(member.getId(), withId.getId());
		}

		InIt inIt = activeInItTimeDetailsQuery.query(source::getInItId, member);
		if (inIt.isOwner(member)) {
			throw new MemberAccessDeniedException(member.getId(), withId.getId());
		}

		WithItTimeDetails timeSource = extractTimeSource(inIt);
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeSource, now);
		if (!period.isValid(now)) {
			throw new TimePolicyException();
		}

		dao.delete(entityConverter.to(source));
		return AbstractResponse.VOID;
	}

	private With getSource(GetWithId withId) {
		Optional<WithEntity> source = dao.findById(withId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetWithId.key, withId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return entityConverter.from(source.get());
	}

	private WithItTimeDetails extractTimeSource(InIt inIt) {
		InItTimeDetails source = inIt.getTime();
		return WithItTimeDetails.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}
}
