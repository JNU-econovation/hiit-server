package com.hiit.api.domain.usecase.end.it;

import com.hiit.api.domain.dao.hit.HitDao;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.end.GetEndWithsUseCaseRequest;
import com.hiit.api.domain.dto.response.end.with.EndWithInfo;
import com.hiit.api.domain.dto.response.end.with.EndWithInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithMemberInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.model.with.With;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.domain.support.entity.PeriodUtils;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.it.InItEntityConverter;
import com.hiit.api.domain.usecase.member.MemberEntityConverter;
import com.hiit.api.domain.usecase.with.WithEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetEndWithsUseCase implements AbstractUseCase<GetEndWithsUseCaseRequest> {

	private final WithDao withDao;
	private final WithEntityConverter entityConverter;

	private final MemberDao memberDao;
	private final MemberEntityConverter memberEntityConverter;

	private final InItDao inItDao;
	private final InItEntityConverter inItEntityConverter;

	private final HitDao hitDao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional(readOnly = true)
	public EndWithInfos execute(final GetEndWithsUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long endInItId = request.getEndInItId();

		log.debug("get end withs : m - {}, end - {}", memberId, endInItId);
		List<With> sources = getSources(endInItId, memberId);
		log.debug("end with size : {}", sources.size());

		log.debug("read member : m - {}", memberId);
		Member member = readMember(memberId);
		log.debug("read end init : m - {}, end - {}", memberId, endInItId);
		InIt endInIt = readEndInIt(endInItId, memberId);

		EndWithMemberInfo memberInfo = makeMemberInfo(member, endInIt);
		List<EndWithInfo> endWithInfos = new ArrayList<>();
		for (With source : sources) {
			Period period =
					PeriodUtils.make(source.getTimeInfo(), source.getCreateAt(), source.getUpdateAt());
			Long hitCount = readHitCount(source, period);
			endWithInfos.add(makeWithInfo(source, memberInfo, hitCount));
		}

		return buildResponse(endWithInfos);
	}

	private List<With> getSources(Long endInItId, Long memberId) {
		return withDao.findAllByInItAndMember(endInItId, memberId).stream()
				.map(entityConverter::from)
				.collect(Collectors.toList());
	}

	private EndWithInfos buildResponse(List<EndWithInfo> sources) {
		return new EndWithInfos(sources);
	}

	private Member readMember(Long memberId) {
		Optional<HiitMemberEntity> source = memberDao.findById(memberId);
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("memberId", memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return memberEntityConverter.from(source.get());
	}

	private InIt readEndInIt(Long endInItId, Long memberId) {
		return inItEntityConverter.from(inItDao.findEndStatusByIdAndMember(endInItId, memberId));
	}

	private EndWithMemberInfo makeMemberInfo(Member member, InIt endInIt) {
		return EndWithMemberInfo.builder()
				.id(member.getId())
				.profile(member.getProfile())
				.name(member.getNickName())
				.resolution(endInIt.getResolution())
				.build();
	}

	private Long readHitCount(With with, Period period) {
		return hitDao.countHitStatusByWithAndPeriod(with.getId(), period);
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
