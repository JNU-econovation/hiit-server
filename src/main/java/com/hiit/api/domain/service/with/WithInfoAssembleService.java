package com.hiit.api.domain.service.with;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dao.support.PageData;
import com.hiit.api.domain.dao.support.PageableInfo;
import com.hiit.api.domain.dao.support.Period;
import com.hiit.api.domain.dao.with.WithData;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithInfoAssembleService {

	private final HitDao hitDao;

	public PageImpl<WithInfo> execute(
			PageData<WithData> source, InItData inIt, WithMemberInfo memberInfo, PageableInfo pageable) {
		List<WithInfo> withInfos = new ArrayList<>();
		for (WithData w : source.getData()) {
			Long hitCount = readHitCount(inIt);
			WithInfo withInfo = makeWithInfo(w, hitCount, memberInfo);
			withInfos.add(withInfo);
		}
		return new PageImpl<>(withInfos, pageable, withInfos.size());
	}

	private Long readHitCount(InItData inIt) {
		return hitDao.countHitByInItAndPeriod(inIt.getId(), Period.today());
	}

	private WithInfo makeWithInfo(WithData w, Long hitCount, WithMemberInfo memberInfo) {
		return WithInfo.builder()
				.id(w.getId())
				.content(w.getContent())
				.hit(hitCount)
				.withMemberInfo(memberInfo)
				.build();
	}
}
