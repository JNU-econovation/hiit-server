package com.hiit.api.domain.usecase.member;

import com.hiit.api.common.token.AuthToken;
import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.CreateSocialMemberUseCaseRequest;
import com.hiit.api.domain.model.member.CertificationSubjectDetails;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.service.token.UserTokenGenerator;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.domain.usecase.member.event.CreateMemberEventPublisher;
import com.hiit.api.repository.entity.business.member.CertificationSubject;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacadeCreateMemberUseCase
		implements AbstractUseCase<CreateSocialMemberUseCaseRequest> {

	private final List<String> TEMP_PROFILE_IMAGES =
			List.of(
					"https://github.com/JNU-econovation/hiit-server/assets/102807742/b6bcc673-6e13-4da5-a32c-5e73c058c785",
					"https://github.com/JNU-econovation/hiit-server/assets/102807742/135cd92a-60cd-4abe-ac6e-7f9619a8217d",
					"https://github.com/JNU-econovation/hiit-server/assets/102807742/51a08eff-d365-4162-9454-7f3b47ebce4d",
					"https://github.com/JNU-econovation/hiit-server/assets/102807742/2251673f-444f-4be2-a84e-267edc90bb4a");

	private final CreateKaKaoMemberUseCase createKaKaoMemberUseCase;

	private final MemberDao dao;

	private final UserTokenGenerator userTokenGenerator;

	private final CreateMemberEventPublisher publisher;

	@Override
	@Transactional
	public AuthToken execute(CreateSocialMemberUseCaseRequest request) {
		final String code = request.getCode();
		final CertificationSubjectDetails certificationSubjectDetails =
				request.getCertificationSubjectDetails();
		Member member = null;
		if (certificationSubjectDetails.equals(CertificationSubjectDetails.KAKAO)) {
			member = createKaKaoMemberUseCase.execute(code);
		}

		Objects.requireNonNull(member);
		final String nickname = member.getNickName();
		final String certificationId = member.getCertificationId();

		Optional<HiitMemberEntity> isExist = dao.findByCertificationId(certificationId);
		// login
		if (isExist.isPresent()) {
			Long memberId = isExist.get().getId();
			return userTokenGenerator.generateAuthToken(memberId);
		}

		// sign up
		HiitMemberEntity source =
				HiitMemberEntity.builder()
						.certificationSubject(CertificationSubject.valueOf(certificationSubjectDetails.name()))
						.nickName(nickname)
						.certificationId(certificationId)
						.profile(TEMP_PROFILE_IMAGES.get((int) (Math.random() * 4)))
						.build();
		Long memberId = dao.save(source).getId();
		publisher.publish(memberId);
		return userTokenGenerator.generateAuthToken(memberId);
	}
}
