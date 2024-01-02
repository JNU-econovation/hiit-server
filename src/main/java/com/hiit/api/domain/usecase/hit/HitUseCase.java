package com.hiit.api.domain.usecase.hit;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.hit.HitUseCaseRequest;
import com.hiit.api.domain.dto.response.hit.HitInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.hit.Hit;
import com.hiit.api.domain.model.hit.HitItTimeInfo;
import com.hiit.api.domain.model.hit.HitterInfo;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeInfo;
import com.hiit.api.domain.service.ItTimeInfoMapper;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.hit.HitEntityConverterImpl;
import com.hiit.api.domain.support.entity.converter.with.WithEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitUseCase implements AbstractUseCase<HitUseCaseRequest> {

	private static int TOP_INDEX = 0;

	private final HitDao hitDao;
	private final HitEntityConverterImpl entityConverter;

	private final WithDao withDao;
	private final WithEntityConverterImpl withEntityConverter;

	private final ItTimeInfoMapper itTimeInfoMapper;
	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	// todo hit 요청이 연속적인 경우 개선 사항 생각해보기 (ex. 1초 이내에 2번 이상 요청)
	@Override
	public HitInfo execute(final HitUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long withId = request.getWithId();

		HitterInfo hitter = HitterInfo.of(memberId);
		log.debug("get with : w - {}", withId);
		With with = readWith(withId);
		// todo fix: with에 상태(ON, OFF)를 추가하여 상태가 OFF인 경우 hit 요청을 받지 않도록 개선
		HitItTimeInfo timeSource = extractTimeSource(with);
		// todo fix: with의 생성 날짜의 0시를 시작으로 현재를 마감으로 하는 기간을 만들어서 사용
		Period period = PeriodUtils.make(timeSource, LocalDateTime.now());
		log.debug("with time : start - {}, end - {}", period.getStart(), period.getEnd());

		log.debug("get hit : w - {}, h(m) - {}", withId, hitter.getId());
		List<Hit> sources = getSources(withId, hitter, period);

		// todo saveAndFlush 동작 확인하기
		if (sources.isEmpty()) {
			log.debug("not hit yet to HIT");
			hitDao.saveAndFlush(entityConverter.to(Hit.hit(with.getId(), hitter)));
			Long count = calcHitCount(with, period);
			return buildResponse(count);
		}
		log.debug("hit already to MISS");
		Hit hit = sources.get(TOP_INDEX);
		hitDao.saveAndFlush(entityConverter.to(hit.miss()));
		Long count = calcHitCount(with, period);
		return buildResponse(count);
	}

	private List<Hit> getSources(Long withId, HitterInfo hitter, Period period) {
		return hitDao.findHitStatusByWithAndHitterAndPeriod(withId, hitter, period).stream()
				.map(entityConverter::from)
				.collect(Collectors.toList());
	}

	private HitInfo buildResponse(Long count) {
		return HitInfo.builder().count(count).build();
	}

	private With readWith(Long withId) {
		Optional<WithEntity> source = withDao.findById(withId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("withId", withId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		String info = source.get().getInIt().getInfo();
		WithItTimeInfo timeInfo = itTimeInfoMapper.read(info, WithItTimeInfo.class);
		return withEntityConverter.from(source.get(), timeInfo);
	}

	private HitItTimeInfo extractTimeSource(With with) {
		WithItTimeInfo source = with.getTimeInfo();
		return HitItTimeInfo.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}

	private Long calcHitCount(With with, Period period) {
		return hitDao.countHitStatusByWithAndPeriod(with.getId(), period);
	}
}
