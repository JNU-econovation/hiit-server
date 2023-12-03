package com.hiit.api.repository.entity.business.it;

import com.hiit.api.repository.entity.BaseEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Entity(name = "in_it")
@Table(name = InItEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE in_it_tb SET deleted=true, in_it_status='END' where id=?")
@Where(clause = "deleted=false")
public class InItEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "in_it_";

	@Column(name = ENTITY_PREFIX + "title", nullable = false, length = 15)
	private String title;

	@Column(name = ENTITY_PREFIX + "resolution", nullable = false, length = 15)
	private String resolution;

	@Convert(converter = DayCodeListConverter.class)
	@Column(name = ENTITY_PREFIX + "day_code", nullable = false)
	private DayCodeList dayCode;

	@Enumerated(EnumType.STRING)
	@Column(name = ENTITY_PREFIX + "status", nullable = false)
	private ItStatus status;

	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = HiitMemberEntity.ENTITY_PREFIX + "fk",
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private HiitMemberEntity hiitMember;

	@Exclude
	@OneToOne(mappedBy = "inIt", cascade = CascadeType.ALL, orphanRemoval = true)
	private ItRelationEntity itRelationEntity;

	public void associate(ItRelationEntity itRelationEntity) {
		this.itRelationEntity = itRelationEntity;
	}
}
