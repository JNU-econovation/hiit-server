package com.hiit.api.domain.usecase.hit;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dto.request.hit.HitUseCaseRequest;
import com.hiit.api.domain.dto.response.hit.HitInfo;
import com.hiit.api.domain.model.hit.Hit;
import com.hiit.api.domain.model.hit.HitItTimeDetails;
import com.hiit.api.domain.model.hit.HitterDetail;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.with.GetWithId;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.model.with.WithItTimeDetails;
import com.hiit.api.domain.service.with.WithQuery;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.support.entity.converter.hit.HitEntityConverterImpl;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitUseCase implements AbstractUseCase<HitUseCaseRequest> {

	private static int TOP_INDEX = 0;

	private final HitDao dao;
	private final HitEntityConverterImpl entityConverter;

	private final WithQuery hitReadTimeWithService;

	// todo hit 요청이 연속적인 경우 개선 사항 생각해보기 (ex. 1초 이내에 2번 이상 요청)
	@Override
	public HitInfo execute(final HitUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetWithId withId = request::getWithId;

		HitterDetail hitter = HitterDetail.of(memberId.getId());
		With with = hitReadTimeWithService.query(withId);
		// todo fix: with에 상태(ON, OFF)를 추가하여 상태가 OFF인 경우 hit 요청을 받지 않도록 개선
		HitItTimeDetails timeSource = extractTimeSource(with);
		// todo fix: with의 생성 날짜의 0시를 시작으로 현재를 마감으로 하는 기간을 만들어서 사용
		Period period = PeriodUtils.make(timeSource, LocalDateTime.now());

		List<Hit> sources = getSources(withId, hitter, period);

		if (sources.isEmpty()) {
			dao.saveAndFlush(entityConverter.to(Hit.hit(with.getId(), hitter)));
			Long count = dao.countHitStatusByWithAndPeriod(with.getId(), period);
			return buildResponse(count);
		}
		Hit hit = sources.get(TOP_INDEX);
		dao.saveAndFlush(entityConverter.to(hit.getId(), hit.miss()));
		Long count = dao.countHitStatusByWithAndPeriod(with.getId(), period);
		return buildResponse(count);
	}

	private List<Hit> getSources(GetWithId withId, HitterDetail hitter, Period period) {
		return dao.findHitStatusByWithAndHitterAndPeriod(withId.getId(), hitter, period).stream()
				.map(entityConverter::from)
				.collect(Collectors.toList());
	}

	private HitInfo buildResponse(Long count) {
		return HitInfo.builder().count(count).build();
	}

	private HitItTimeDetails extractTimeSource(With with) {
		WithItTimeDetails source = with.getTime();
		return HitItTimeDetails.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}
}
