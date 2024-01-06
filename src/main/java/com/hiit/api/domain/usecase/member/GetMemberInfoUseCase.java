package com.hiit.api.domain.usecase.member;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.GetMemberInfoUseCaseRequest;
import com.hiit.api.domain.dto.response.member.MemberInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.document.member.MemberStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetMemberInfoUseCase implements AbstractUseCase<GetMemberInfoUseCaseRequest> {

	private final MemberDao memberDao;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public MemberInfo execute(GetMemberInfoUseCaseRequest request) {
		final Long memberId = request.getMemberId();

		Optional<MemberStatDoc> docs = memberDao.findMemberStatDocByMemberId(memberId);
		if (docs.isEmpty()) {
			Map<String, Long> exceptionSource = logSourceGenerator.generate("memberId", memberId);
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		MemberStat source = docs.get().getResource();

		HiitMemberEntity member = memberDao.findById(source.getId()).orElse(null);
		assert member != null;

		return MemberInfo.builder()
				.id(member.getId())
				.name(member.getNickName())
				.profile(member.getProfile())
				.inItCount(source.getTotalItCount())
				.build();
	}
}
