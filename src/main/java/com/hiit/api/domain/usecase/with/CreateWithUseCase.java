package com.hiit.api.domain.usecase.with;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.with.CreateWithUseCaseRequest;
import com.hiit.api.domain.exception.CreateCountPolicyException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.exception.TimePolicyException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeDetails;
import com.hiit.api.domain.service.it.in.ActiveInItTimeDetailsQueryImpl;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.with.WithEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.with.event.CreateWithEventPublisher;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateWithUseCase implements AbstractUseCase<CreateWithUseCaseRequest> {

	private static final int MAX_CREATE_COUNT = 1;

	private final WithDao dao;
	private final WithEntityConverterImpl entityConverter;

	private final MemberQuery memberQuery;

	private final ActiveInItTimeDetailsQueryImpl activeInItTimeDetailsQuery;

	private final CreateWithEventPublisher publisher;

	@Override
	@Transactional
	public AbstractResponse execute(final CreateWithUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId inItId = request::getInItId;
		final String content = request.getContent();

		GetMemberId member = memberQuery.query(memberId);
		InIt inIt = activeInItTimeDetailsQuery.query(inItId, memberId);
		if (!inIt.isOwner(member)) {
			throw new MemberAccessDeniedException(member.getId(), inItId.getId());
		}

		WithItTimeDetails timeSource = extractTimeSource(inIt);
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeSource, now);
		if (!period.isValid(now)) {
			throw new TimePolicyException();
		}

		Optional<With> source = getSource(inIt, memberId.getId(), period);
		if (source.isPresent()) {
			throw new CreateCountPolicyException(MAX_CREATE_COUNT);
		}
		With with = makeWith(content, inIt);
		Long withId =
				dao.save(entityConverter.to(with).toBuilder().memberId(memberId.getId()).build()).getId();
		publisher.publish(memberId.getId(), inItId.getId(), withId);
		return AbstractResponse.VOID;
	}

	private Optional<With> getSource(InIt inIt, Long memberId, Period period) {
		Optional<WithEntity> source =
				dao.findByInItEntityAndMemberAndPeriod(inIt.getId(), memberId, period);
		if (source.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(entityConverter.from(source.get()));
	}

	private WithItTimeDetails extractTimeSource(InIt inIt) {
		InItTimeDetails source = inIt.getTime();
		return WithItTimeDetails.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}

	private With makeWith(String content, InIt inIt) {
		return With.builder().content(content).inItId(inIt.getId()).build();
	}
}
