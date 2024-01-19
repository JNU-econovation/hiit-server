package com.hiit.api.domain.service.with;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.usecase.member.MemberEntityConverter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** WithInfo 생성을 위해 필요한 정보를 조합하는 서비스 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WithInfoAssembleService {

	private final HitDao hitDao;
	private final InItDao inItDao;
	private final MemberDao memberDao;

	private final MemberEntityConverter memberEntityConverter;

	@Transactional(readOnly = true)
	public PageImpl<WithInfo> execute(PageElements<With> withs, PageRequest pageable) {
		List<WithInfo> sources = new ArrayList<>();
		for (With with : withs.getData()) {
			Long inItId = with.getInItId();
			Long memberId = with.getMemberId();
			InItEntity inIt = inItDao.findById(inItId).orElseThrow();
			Long hitCount = readHitCount(inIt.getId());
			Member member = memberEntityConverter.from(memberDao.findById(memberId).orElseThrow());
			WithMemberInfo withMemberInfo = makeMemberInfo(member, inIt.getResolution());
			WithInfo withInfo = makeWithInfo(with, hitCount, withMemberInfo);
			sources.add(withInfo);
		}
		return new PageImpl<>(sources, pageable, sources.size());
	}

	private Long readHitCount(Long inItId) {
		return hitDao.countHitByInItAndPeriod(inItId, Period.today());
	}

	private WithInfo makeWithInfo(With with, Long hitCount, WithMemberInfo memberInfo) {
		return WithInfo.builder()
				.id(with.getId())
				.content(with.getContent())
				.hit(hitCount)
				.withMemberInfo(memberInfo)
				.build();
	}

	private WithMemberInfo makeMemberInfo(Member member, String resolution) {
		return WithMemberInfo.builder()
				.memberId(member.getId())
				.name(member.getNickName())
				.profile(member.getProfile())
				.resolution(resolution)
				.build();
	}
}
