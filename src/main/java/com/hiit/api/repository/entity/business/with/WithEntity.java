package com.hiit.api.repository.entity.business.with;

import com.hiit.api.repository.entity.BaseEntity;
import com.hiit.api.repository.entity.business.it.InItEntity;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Entity(name = "with")
@Table(name = WithEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE with_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class WithEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "with_";

	@Column(name = ENTITY_PREFIX + "content", nullable = false, length = 20)
	private String content;

	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = InItEntity.ENTITY_PREFIX + "fk",
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private InItEntity inIt;

	@Column(name = ENTITY_PREFIX + "memberId", nullable = false, length = 20)
	private Long memberId;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = ENTITY_PREFIX + "status", nullable = false)
	private WithStatus status = WithStatus.ACTIVE;

	// todo with 상태(ON, OFF) 추가, 상태 갱신을 위한 배치 작업 필요
}
