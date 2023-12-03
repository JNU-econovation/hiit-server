package com.hiit.api.repository.init.member;

import com.hiit.api.repository.entity.business.member.CertificationSubject;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.ArrayList;
import java.util.List;

public class FakeHiitMember {

	public FakeHiitMember() {}

	public static HiitMemberEntity create() {
		return HiitMemberEntity.builder()
				.nickName("tNicName")
				.profile("tProfile")
				.certificationId("tCertificationId")
				.certificationSubject(CertificationSubject.KAKAO)
				.notificationConsent(true)
				.build();
	}

	public static List<HiitMemberEntity> createMany(int i) {
		List<HiitMemberEntity> sources = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			HiitMemberEntity source =
					HiitMemberEntity.builder()
							.nickName("tNicName" + i)
							.profile("tProfile" + i)
							.certificationId("tCertificationId" + i)
							.certificationSubject(CertificationSubject.KAKAO)
							.notificationConsent(true)
							.build();
			sources.add(source);
		}
		return sources;
	}
}
