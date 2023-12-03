package com.hiit.api.repository.entity.business.it;

import com.hiit.api.repository.entity.IdBaseEntity;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@Entity(name = "it_relation")
@Table(name = ItRelationEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE it_relation_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class ItRelationEntity extends IdBaseEntity {

	public static final String ENTITY_PREFIX = "it_relation_";

	@Column(name = ENTITY_PREFIX + "target_it_id", nullable = false)
	private Long targetItId;

	@Enumerated(EnumType.STRING)
	@Column(name = ENTITY_PREFIX + "target_it_type", nullable = false)
	private TargetItType targetItType;

	@OneToOne
	@JoinColumn(
			name = InItEntity.ENTITY_PREFIX + "fk",
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private InItEntity inIt;

	public boolean isTargetRootType() {
		return targetItType.getRoot();
	}
}
