package com.hiit.api.domain.usecase.member;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.DissentNotificationUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DissentNotificationUseCase
		implements AbstractUseCase<DissentNotificationUseCaseRequest> {

	private final MemberDao dao;

	private final MemberEntityConverter entityConverter;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	public AbstractResponse execute(DissentNotificationUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;

		Member member = getSource(memberId);

		member.dissentNotification();
		HiitMemberEntity entity = entityConverter.to(member.getId(), member);
		dao.save(entity);
		return AbstractResponse.VOID;
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
}
