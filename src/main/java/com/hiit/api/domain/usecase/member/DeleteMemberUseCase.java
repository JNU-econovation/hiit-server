package com.hiit.api.domain.usecase.member;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.client.member.unlink.KaKaoUnlinkClient;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.DeleteMemberUseCaseRequest;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.model.member.CertificationSubjectDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.member.MemberNotificationInfoEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteMemberUseCase implements AbstractUseCase<DeleteMemberUseCaseRequest> {

	private final MemberDao dao;
	private final MemberEntityConverter entityConverter;

	private final KaKaoUnlinkClient kaKaoUnlinkClient;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	@Override
	@Transactional
	public AbstractResponse execute(DeleteMemberUseCaseRequest request) {
		final GetMemberId memberId = request::getMemberId;
		Member member = getSource(memberId);
		member.withdraw();
		unlink(member);
		HiitMemberEntity entity = entityConverter.to(member.getId(), member);
		dao.saveAndFlush(entity);
		dao.delete(entity);
		List<MemberNotificationInfoEntity> notificationInfoEntities =
				dao.findAllNotificationInfoByHiitMemberEntity(entity);
		dao.deleteNotificationInfos(notificationInfoEntities);
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

	private void unlink(Member member) {
		CertificationSubjectDetails certificationSubject = member.getCertificationSubject();
		if (certificationSubject == CertificationSubjectDetails.KAKAO) {
			String certificationId = member.getCertificationId();
			kaKaoUnlinkClient.execute(certificationId);
		}
	}
}
