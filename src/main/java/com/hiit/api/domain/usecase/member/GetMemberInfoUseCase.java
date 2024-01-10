package com.hiit.api.domain.usecase.member;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.GetMemberInfoUseCaseRequest;
import com.hiit.api.domain.dto.response.member.MemberInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.member.Member;
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

	private final MemberDao dao;
	private final MemberEntityConverter entityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public MemberInfo execute(GetMemberInfoUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;

		Member source = getSource(memberId);

		MemberStat docs = readDocs(memberId);

		return MemberInfo.builder()
				.id(source.getId())
				.name(source.getNickName())
				.profile(source.getProfile())
				.inItCount(docs.getTotalItCount())
				.build();
	}

	private Member getSource(GetMemberId memberId) {
		Optional<HiitMemberEntity> source = dao.findById(memberId.getId());
		if (source.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetMemberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return entityConverter.from(source.get());
	}

	private MemberStat readDocs(GetMemberId memberId) {
		Optional<MemberStatDoc> docs = dao.findMemberStatDocByMemberId(memberId.getId());
		if (docs.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetMemberId.key, memberId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return docs.get().getResource();
	}
}
