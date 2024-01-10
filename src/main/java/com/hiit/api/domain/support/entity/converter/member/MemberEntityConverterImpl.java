package com.hiit.api.domain.support.entity.converter.member;

import com.hiit.api.domain.model.member.CertificationSubjectDetails;
import com.hiit.api.domain.model.member.Member;
import com.hiit.api.domain.model.member.MemberStatusDetails;
import com.hiit.api.domain.usecase.member.MemberEntityConverter;
import com.hiit.api.repository.entity.business.member.CertificationSubject;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.member.MemberStatus;
import org.springframework.stereotype.Component;

@Component
public class MemberEntityConverterImpl implements MemberEntityConverter {

	@Override
	public Member from(HiitMemberEntity entity) {
		return Member.builder()
				.id(entity.getId())
				.nickName(entity.getNickName())
				.profile(entity.getProfile())
				.certificationId(entity.getCertificationId())
				.certificationSubject(
						CertificationSubjectDetails.valueOf(entity.getCertificationSubject().name()))
				.status(MemberStatusDetails.valueOf(entity.getStatus().name()))
				.notificationConsent(entity.getNotificationConsent())
				.resource(entity.getResource())
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.build();
	}

	@Override
	public HiitMemberEntity to(Member data) {
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

	@Override
	public HiitMemberEntity to(Long id, Member data) {
		return to(data).toBuilder().id(id).build();
	}
}
