package com.hiit.api.domain.usecase.member;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.GetMemberItInfoUseCaseRequest;
import com.hiit.api.domain.dto.response.member.MemberItInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.service.it.ItQueryManager;
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

	private final MemberDao dao;
	private final MemberEntityConverter entityConverter;

	private final ItQueryManager itQueryManager;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public MemberItInfo execute(GetMemberItInfoUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		final GetInItId inItId = request::getItId;

		Member source = getSource(memberId);

		ItWithStat docs = readDocs(source, inItId);

		BasicIt it =
				itQueryManager.query(ItTypeDetails.of(docs.getType()), (GetInItId) docs::getInItId);
		String topic = it.getTopic();
		String itInfo = topic + "에 " + docs.getWithCount() + "번 참여했어요!";

		return MemberItInfo.builder()
				.id(source.getId())
				.name(source.getNickName())
				.profile(source.getProfile())
				.itInfo(itInfo)
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

	private ItWithStat readDocs(GetMemberId memberId, GetInItId inItId) {
		Optional<ItWithStat> docs =
				dao.findItWithStatByMemberIdAndInItId(memberId.getId(), inItId.getId());
		if (docs.isEmpty()) {
			Map<String, Long> exceptionSource =
					logSourceGenerator.generate(GetMemberId.key, memberId.getId());
			exceptionSource = logSourceGenerator.add(exceptionSource, GetInItId.key, inItId.getId());
			String exceptionData = jsonConverter.toJson(exceptionSource);
			throw new DataNotFoundException(exceptionData);
		}
		return docs.get();
	}
}
