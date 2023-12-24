package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dao.member.MemberData;
import com.hiit.api.domain.dao.support.Period;
import com.hiit.api.domain.dao.support.PeriodUtils;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dao.with.WithData;
import com.hiit.api.domain.dto.request.end.GetEndWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.with.EndWithInfo;
import com.hiit.api.domain.dto.response.end.with.EndWithInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithMemberInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.usecase.AbstractUseCase;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEndWithsUseCase implements AbstractUseCase<GetEndWithsUseCaseRequest> {

	private final WithDao withDao;

	private final MemberDao memberDao;
	private final InItDao inItDao;
	private final HitDao hitDao;

	@Override
	@Transactional(readOnly = true)
	public EndWithInfos execute(GetEndWithsUseCaseRequest request) {
		Long memberId = request.getMemberId();
		Long endInItId = request.getEndInItId();

		List<WithData> withs = getWiths(endInItId, memberId);

		MemberData member = readMember(memberId);
		InItData endInIt = readEndInIt(endInItId, memberId);

		EndWithMemberInfo memberInfo = makeMemberInfo(member, endInIt);
		List<EndWithInfo> source = new ArrayList<>();
		for (WithData with : withs) {
			Period period = PeriodUtils.make(with);
			Long hitCount = readHitCount(with, period);
			source.add(makeWithInfo(with, memberInfo, hitCount));
		}

		return buildResponse(source);
	}

	private List<WithData> getWiths(Long endInItId, Long memberId) {
		return withDao.findAllByInItAndMember(endInItId, memberId);
	}

	private EndWithInfos buildResponse(List<EndWithInfo> source) {
		return new EndWithInfos(source);
	}

	private MemberData readMember(Long memberId) {
		return memberDao
				.findById(memberId)
				.orElseThrow(() -> new DataNotFoundException("Member id : " + memberId));
	}

	private InItData readEndInIt(Long endInItId, Long memberId) {
		return inItDao.findEndStatusByIdAndMember(endInItId, memberId);
	}

	private EndWithMemberInfo makeMemberInfo(MemberData member, InItData endInIt) {
		return EndWithMemberInfo.builder()
				.id(member.getId())
				.profile(member.getProfile())
				.name(member.getNickName())
				.resolution(endInIt.getResolution())
				.build();
	}

	private Long readHitCount(WithData with, Period period) {
		return hitDao.countHitStatusByWithAndPeriod(with.getId(), period);
	}

	private EndWithInfo makeWithInfo(WithData with, EndWithMemberInfo memberInfo, Long hitCount) {
		return EndWithInfo.builder()
				.id(with.getId())
				.content(with.getContent())
				.hit(hitCount)
				.withMemberInfo(memberInfo)
				.build();
	}
}
