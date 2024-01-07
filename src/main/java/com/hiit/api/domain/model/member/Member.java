package com.hiit.api.domain.model.member;

import com.hiit.api.common.marker.model.AbstractDomain;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Member implements AbstractDomain {

	private Long id;

	private String nickName;
	private String profile;
	private String certificationId;
	private CertificationSubjectInfo certificationSubject;
	@Builder.Default private MemberStatusInfo status = MemberStatusInfo.REGULAR;
	private Boolean notificationConsent;
	private String resource;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public boolean isSameId(Long id) {
		return this.id.equals(id);
	}
}
