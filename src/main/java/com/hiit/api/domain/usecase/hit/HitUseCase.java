package com.hiit.api.domain.usecase.hit;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.hit.HitData;
import com.hiit.api.domain.dao.hit.HitterInfo;
import com.hiit.api.domain.dao.support.Period;
import com.hiit.api.domain.dao.support.PeriodUtils;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dao.with.WithData;
import com.hiit.api.domain.dto.request.hit.HitUseCaseRequest;
import com.hiit.api.domain.dto.response.hit.HitInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.service.hit.HitItTimeInfo;
import com.hiit.api.domain.service.hit.HitItTimeInfoService;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitUseCase implements AbstractUseCase<HitUseCaseRequest> {

	private final HitDao hitDao;
	private final WithDao withDao;

	private final HitItTimeInfoService hitItTimeInfoService;

	// todo hit 요청이 연속적인 경우 개선 사항 생각해보기 (ex. 1초 이내에 2번 이상 요청)
	@Override
	public HitInfo execute(HitUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long withId = request.getWithId();

		HitterInfo hitter = HitterInfo.of(memberId);
		WithData with = readWith(withId);
		HitItTimeInfo timeSource = readTimeSource(with);
		Period period = PeriodUtils.make(timeSource);

		Optional<HitData> isHit = getHit(withId, hitter, period);

		// todo saveAndFlush 동작 확인하기
		if (isHit.isEmpty()) {
			hitDao.saveAndFlush(HitData.hit(with.getId(), hitter));
			Long count = calcHitCount(with, period);
			return buildResponse(count);
		}
		hitDao.saveAndFlush(HitData.miss(with.getId(), hitter));
		Long count = calcHitCount(with, period);
		return buildResponse(count);
	}

	private Optional<HitData> getHit(Long withId, HitterInfo hitter, Period period) {
		return hitDao.findHitStatusByWithAndHitterAndPeriod(withId, hitter, period);
	}

	private HitInfo buildResponse(Long count) {
		return HitInfo.builder().count(count).build();
	}

	private WithData readWith(Long withId) {
		return withDao
				.findById(withId)
				.orElseThrow(() -> new DataNotFoundException("With id :" + withId));
	}

	private HitItTimeInfo readTimeSource(WithData with) {
		return hitItTimeInfoService.read(with);
	}

	private Long calcHitCount(WithData with, Period period) {
		return hitDao.countHitStatusByWithAndPeriod(with.getId(), period);
	}
}
