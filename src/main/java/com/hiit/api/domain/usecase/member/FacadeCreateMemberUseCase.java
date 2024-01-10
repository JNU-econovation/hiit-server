package com.hiit.api.domain.usecase.member;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.CreateSocialMemberUseCaseRequest;
import com.hiit.api.domain.dto.response.member.UserAuthToken;
import com.hiit.api.domain.model.member.CertificationSubjectInfo;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.service.token.UserTokenGenerator;
import com.hiit.api.domain.usecase.AbstractUseCase;
import com.hiit.api.repository.entity.business.member.CertificationSubject;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
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

	private final CreateKaKaoMemberUseCase createKaKaoMemberUseCase;

	private final MemberDao memberDao;
	private final UserTokenGenerator userTokenGenerator;

	@Override
	@Transactional
	public UserAuthToken execute(CreateSocialMemberUseCaseRequest request) {
		final String code = request.getCode();
		final CertificationSubjectInfo certificationSubjectInfo = request.getCertificationSubjectInfo();
		Member member = null;
		if (certificationSubjectInfo.equals(CertificationSubjectInfo.KAKAO)) {
			member = createKaKaoMemberUseCase.execute(code);
		}

		member = Objects.requireNonNull(member);
		final String nickname = member.getNickName();
		final String certificationId = member.getCertificationId();

		Optional<HiitMemberEntity> isExist = memberDao.findByCertificationId(certificationId);
		// login
		if (isExist.isPresent()) {
			Long memberId = isExist.get().getId();
			return userTokenGenerator.generateAuthToken(memberId);
		}

		// sign up
		HiitMemberEntity source =
				HiitMemberEntity.builder()
						.certificationSubject(CertificationSubject.valueOf(certificationSubjectInfo.name()))
						.nickName(nickname)
						.certificationId(certificationId)
						.build();
		Long memberId = memberDao.saveAndFlush(source).getId();
		return userTokenGenerator.generateAuthToken(memberId);
	}
}
