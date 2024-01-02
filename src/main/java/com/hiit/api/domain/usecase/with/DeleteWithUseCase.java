package com.hiit.api.domain.usecase.with;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.with.DeleteWithUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.exception.TimePolicyException;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItTimeInfo;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeInfo;
import com.hiit.api.domain.service.ItTimeInfoMapper;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.in.it.InItEntityConverterImpl;
import com.hiit.api.domain.support.entity.converter.with.WithEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.it.InItEntity;
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

	private final WithDao withDao;
	private final WithEntityConverterImpl entityConverter;

	private final InItDao inItDao;
	private final InItEntityConverterImpl inItEntityConverter;

	private final ItTimeInfoMapper itTimeInfoMapper;
	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public AbstractResponse execute(final DeleteWithUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long withId = request.getWithId();

		log.debug("get with : w - {}", withId);
		With source = getSource(withId);

		log.debug("get init : m - {}, i - {}", memberId, source.getInItId());
		InIt inIt = readInIt(source, memberId);
		if (inIt.isOwner(memberId)) {
			log.debug("{} is not owner of {}", memberId, source.getInItId());
			throw new MemberAccessDeniedException(memberId, withId);
		}

		WithItTimeInfo timeSource = extractTimeSource(inIt);
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeSource, now);
		if (!period.isValid(now)) {
			log.debug("now invalid period : s - {}, e - {}", period.getStart(), period.getEnd());
			throw new TimePolicyException();
		}

		withDao.delete(entityConverter.to(source));
		return AbstractResponse.VOID;
	}

	private With getSource(Long withId) {
		Optional<WithEntity> source = withDao.findById(withId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("with", withId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return entityConverter.from(source.get());
	}

	private InIt readInIt(With with, Long memberId) {
		InItEntity source = inItDao.findActiveStatusByIdAndMember(with.getInItId(), memberId);
		String info = source.getInfo();
		InItTimeInfo timeInfo = itTimeInfoMapper.read(info, InItTimeInfo.class);
		return inItEntityConverter.from(source, timeInfo);
	}

	private WithItTimeInfo extractTimeSource(InIt inIt) {
		InItTimeInfo source = inIt.getTimeInfo();
		return WithItTimeInfo.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}
}
