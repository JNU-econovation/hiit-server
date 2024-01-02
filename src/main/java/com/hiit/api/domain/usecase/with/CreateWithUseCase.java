package com.hiit.api.domain.usecase.with;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.with.CreateWithUseCaseRequest;
import com.hiit.api.domain.exception.CreateCountPolicyException;
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
import com.hiit.api.repository.entity.business.it.InItEntity;
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

	private final WithDao withDao;
	private final WithEntityConverterImpl entityConverter;

	private final InItDao inItDao;
	private final InItEntityConverterImpl inItEntityConverter;

	private final ItTimeInfoMapper itTimeInfoMapper;

	@Override
	@Transactional
	public AbstractResponse execute(final CreateWithUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long inItId = request.getInItId();
		final String content = request.getContent();

		log.debug("read init : m - {}, in - {}", memberId, inItId);
		InIt inIt = readInIt(inItId, memberId);
		if (inIt.isOwner(memberId)) {
			log.debug("{} is not owner of {}", memberId, inItId);
			throw new MemberAccessDeniedException(memberId, inItId);
		}

		WithItTimeInfo timeSource = extractTimeSource(inIt);
		LocalDateTime now = LocalDateTime.now();
		Period period = PeriodUtils.make(timeSource, now);
		if (!period.isValid(now)) {
			log.debug("now invalid period : s - {}, e - {}", period.getStart(), period.getEnd());
			throw new TimePolicyException();
		}

		log.debug("get with : m - {}, in - {}", memberId, inItId);
		Optional<With> source = getSource(inIt, memberId, period);

		if (source.isPresent()) {
			throw new CreateCountPolicyException(MAX_CREATE_COUNT);
		}
		With with = makeWith(content, inIt);
		withDao.save(entityConverter.to(with));
		return AbstractResponse.VOID;
	}

	private Optional<With> getSource(InIt inIt, Long memberId, Period period) {
		Optional<WithEntity> entity =
				withDao.findByInItEntityAndMemberAndPeriod(inIt.getId(), memberId, period);
		if (entity.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(entityConverter.from(entity.get()));
	}

	private InIt readInIt(Long inItId, Long memberId) {
		InItEntity source = inItDao.findActiveStatusByIdAndMember(inItId, memberId);
		String info = source.getInfo();
		WithItTimeInfo timeInfo = itTimeInfoMapper.read(info, WithItTimeInfo.class);
		return inItEntityConverter.from(source, timeInfo);
	}

	private WithItTimeInfo extractTimeSource(InIt inIt) {
		InItTimeInfo source = inIt.getTimeInfo();
		return WithItTimeInfo.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}

	private With makeWith(String content, InIt inIt) {
		return With.builder().content(content).inItId(inIt.getId()).build();
	}
}
