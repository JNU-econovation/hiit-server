package com.hiit.api.domain.dao.member;

import com.hiit.api.domain.dao.BaseData;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class MemberData extends BaseData {

	private String nickName;
	private String profile;
	private String certificationId;
	private CertificationSubjectInfo certificationSubject;
	@Builder.Default private MemberStatusInfo status = MemberStatusInfo.REGULAR;
	private Boolean notificationConsent;
	private String resource;
}
