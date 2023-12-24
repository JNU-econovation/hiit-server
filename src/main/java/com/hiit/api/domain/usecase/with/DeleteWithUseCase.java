package com.hiit.api.domain.usecase.with;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dao.support.Period;
import com.hiit.api.domain.dao.support.PeriodUtils;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dao.with.WithData;
import com.hiit.api.domain.dto.request.with.DeleteWithUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.exception.TimePolicyException;
import com.hiit.api.domain.service.with.WithItTimeInfo;
import com.hiit.api.domain.service.with.WithItTimeInfoService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteWithUseCase implements AbstractUseCase<DeleteWithUseCaseRequest> {

	private final WithDao withDao;
	private final InItDao inItDao;

	private final WithItTimeInfoService withItTimeInfoService;

	@Override
	@Transactional
	public ServiceResponse execute(DeleteWithUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long withId = request.getWithId();

		WithData with = getWith(withId);
		Long inItId = with.getInItId();
		InItData inIt = browseInIt(inItId, memberId);
		if (!inIt.isOwner(memberId)) {
			throw new MemberAccessDeniedException("not match memberId");
		}

		WithItTimeInfo timeInfoSource = readTimeSource(with);
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeInfoSource, now);
		if (!period.isValid(now)) {
			throw new TimePolicyException(period, now);
		}

		withDao.delete(with);
		return ServiceResponse.VOID;
	}

	private WithData getWith(Long withId) {
		return withDao
				.findById(withId)
				.orElseThrow(() -> new DataNotFoundException("With id : " + withId));
	}

	private InItData browseInIt(Long inItId, Long memberId) {
		return inItDao.findActiveStatusByIdAndMember(inItId, memberId);
	}

	private WithItTimeInfo readTimeSource(WithData with) {
		return withItTimeInfoService.read(with);
	}
}
