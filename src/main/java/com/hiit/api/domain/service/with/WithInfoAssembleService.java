package com.hiit.api.domain.service.with;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

/** WithInfo 생성을 위해 필요한 정보를 조합하는 서비스 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WithInfoAssembleService {

	private final HitDao hitDao;

	public PageImpl<WithInfo> execute(
			PageElements<With> withs, InIt inIt, WithMemberInfo memberInfo, PageRequest pageable) {
		log.debug(
				"on service assemble with info : w - {}, in - {}, m - {}",
				withs.getTotalCount(),
				inIt.getId(),
				memberInfo.getName());
		List<WithInfo> sources = new ArrayList<>();
		for (With with : withs.getData()) {
			Long hitCount = readHitCount(inIt);
			WithInfo withInfo = makeWithInfo(with, hitCount, memberInfo);
			sources.add(withInfo);
		}
		return new PageImpl<>(sources, pageable, sources.size());
	}

	private Long readHitCount(InIt inIt) {
		return hitDao.countHitByInItAndPeriod(inIt.getId(), Period.today());
	}

	private WithInfo makeWithInfo(With with, Long hitCount, WithMemberInfo memberInfo) {
		return WithInfo.builder()
				.id(with.getId())
				.content(with.getContent())
				.hit(hitCount)
				.withMemberInfo(memberInfo)
				.build();
	}
}
