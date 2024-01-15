package com.hiit.api.repository.entity.business.member;

import com.hiit.api.repository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@Entity(name = "hiit_member_notification_info")
@Table(name = MemberNotificationInfoEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE hiit_member_notification_info_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class MemberNotificationInfoEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "member_noti_info_";

	@Column(name = ENTITY_PREFIX + "device", nullable = false)
	private String device;

	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = HiitMemberEntity.ENTITY_PREFIX + "fk",
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private HiitMemberEntity hiitMemberEntity;
}
