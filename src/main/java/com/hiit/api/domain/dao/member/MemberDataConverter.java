package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.business.member.CertificationSubject;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.member.MemberStatus;
import org.springframework.stereotype.Component;

@Component
public class MemberDataConverter implements AbstractDataConverter<HiitMemberEntity, MemberData> {

	@Override
	public MemberData from(HiitMemberEntity entity) {
		return MemberData.builder()
				.nickName(entity.getNickName())
				.profile(entity.getProfile())
				.certificationId(entity.getCertificationId())
				.certificationSubject(
						CertificationSubjectInfo.valueOf(entity.getCertificationSubject().name()))
				.status(MemberStatusInfo.valueOf(entity.getStatus().name()))
				.notificationConsent(entity.getNotificationConsent())
				.resource(entity.getResource())
				.build();
	}

	public HiitMemberEntity to(MemberData data) {
		return HiitMemberEntity.builder()
				.nickName(data.getNickName())
				.profile(data.getProfile())
				.certificationId(data.getCertificationId())
				.certificationSubject(CertificationSubject.valueOf(data.getCertificationSubject().name()))
				.status(MemberStatus.valueOf(data.getStatus().name()))
				.notificationConsent(data.getNotificationConsent())
				.resource(data.getResource())
				.build();
	}
}
