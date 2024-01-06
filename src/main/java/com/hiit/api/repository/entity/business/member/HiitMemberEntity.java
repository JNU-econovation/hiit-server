package com.hiit.api.repository.entity.business.member;

import com.hiit.api.repository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@Entity(name = "hiit_member")
@Table(name = HiitMemberEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE hiit_member_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class HiitMemberEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "hiit_member_";

	@Column(name = ENTITY_PREFIX + "nick_name", nullable = false)
	private String nickName;

	@Builder.Default
	@Column(name = ENTITY_PREFIX + "profile", nullable = false)
	private String profile = "";

	@Column(name = ENTITY_PREFIX + "certification_id", nullable = false)
	private String certificationId;

	@Enumerated(EnumType.STRING)
	@Column(name = ENTITY_PREFIX + "certification_subject", nullable = false)
	private CertificationSubject certificationSubject;

	@SuppressWarnings("FieldMayBeFinal")
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private MemberStatus status = MemberStatus.REGULAR;

	@Column @Builder.Default private Boolean notificationConsent = false;

	@Column(name = ENTITY_PREFIX + "resource", columnDefinition = "json")
	private String resource;
}
