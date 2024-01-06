package com.hiit.api.domain.usecase.member;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.GetMemberItInfoUseCaseRequest;
import com.hiit.api.domain.dto.response.member.MemberItInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.document.member.ItWithStat;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetMemberItInfoUseCase implements AbstractUseCase<GetMemberItInfoUseCaseRequest> {

	private final MemberDao memberDao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public MemberItInfo execute(GetMemberItInfoUseCaseRequest request) {
		final Long memberId = request.getMemberId();
		final Long itId = request.getItId();

		Optional<ItWithStat> stat = memberDao.findItWithStatByMemberIdAndItId(memberId, itId);
		if (stat.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("memberId", memberId);
			exceptionSource = logSourceGenerator.add(exceptionSource, "itId", itId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}

		HiitMemberEntity member = memberDao.findById(memberId).orElse(null);
		assert member != null;

		ItWithStat source = stat.get();

		return MemberItInfo.builder()
				.id(member.getId())
				.name(member.getNickName())
				.profile(member.getProfile())
				.withCount(source.getWithCount())
				.build();
	}
}
