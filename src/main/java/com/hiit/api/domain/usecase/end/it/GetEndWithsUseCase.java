package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.end.GetEndWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.with.EndWithInfo;
import com.hiit.api.domain.dto.response.end.with.EndWithInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithMemberInfo;
import com.hiit.api.domain.exception.MemberAccessDeniedException;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.service.it.in.InItQueryManager;
import com.hiit.api.domain.service.member.MemberQuery;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.with.WithEntityConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEndWithsUseCase implements AbstractUseCase<GetEndWithsUseCaseRequest> {

	private final WithDao dao;
	private final WithEntityConverter entityConverter;

	private final MemberQuery itMemberQuery;
	private final InItQueryManager inItQueryManager;

	// todo fix
	private final HitDao hitDao;

	@Override
	@Transactional(readOnly = true)
	public EndWithInfos execute(final GetEndWithsUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId endInItId = request::getEndInItId;

		List<With> sources = getSources(endInItId, memberId);

		InIt endInIt = inItQueryManager.query(InItStatusDetails.END, endInItId, memberId);
		if (!endInIt.isOwner(memberId)) {
			throw new MemberAccessDeniedException(memberId.getId(), endInItId.getId());
		}

		Member member = itMemberQuery.query(memberId);
		EndWithMemberInfo memberInfo = makeMemberInfo(member, endInIt);
		List<EndWithInfo> endWithInfos = new ArrayList<>();
		for (With source : sources) {
			Period period =
					PeriodUtils.make(source.getTime(), source.getCreateAt(), source.getUpdateAt());
			Long hitCount = hitDao.countHitStatusByWithAndPeriod(source.getId(), period);
			endWithInfos.add(makeWithInfo(source, memberInfo, hitCount));
		}

		return buildResponse(endWithInfos);
	}

	private List<With> getSources(GetInItId endInItId, GetMemberId memberId) {
		return dao.findAllByInItAndMember(endInItId.getId(), memberId.getId()).stream()
				.map(entityConverter::from)
				.collect(Collectors.toList());
	}

	private EndWithInfos buildResponse(List<EndWithInfo> sources) {
		return new EndWithInfos(sources);
	}

	private EndWithMemberInfo makeMemberInfo(Member member, InIt endInIt) {
		return EndWithMemberInfo.builder()
				.id(member.getId())
				.profile(member.getProfile())
				.name(member.getNickName())
				.resolution(endInIt.getResolution())
				.build();
	}

	private EndWithInfo makeWithInfo(With with, EndWithMemberInfo memberInfo, Long hitCount) {
		return EndWithInfo.builder()
				.id(with.getId())
				.content(with.getContent())
				.hit(hitCount)
				.withMemberInfo(memberInfo)
				.build();
	}
}
