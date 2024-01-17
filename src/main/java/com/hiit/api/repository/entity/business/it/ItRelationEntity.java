package com.hiit.api.repository.entity.business.it;

import com.hiit.api.repository.entity.IdBaseEntity;
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
@Entity(name = "it_relation")
@Table(name = ItRelationEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE it_relation_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class ItRelationEntity extends IdBaseEntity {

	public static final String ENTITY_PREFIX = "it_relation_";

	@Column(name = ENTITY_PREFIX + "target_it_id", nullable = false)
	private Long itId;

	@Enumerated(EnumType.STRING)
	@Column(name = ENTITY_PREFIX + "target_it_type", nullable = false)
	private ItType itType;

	@Column(name = InItEntity.ENTITY_PREFIX + "fk", nullable = false)
	private Long inItId;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = ENTITY_PREFIX + "status", nullable = false)
	private ItStatus status = ItStatus.ACTIVE;

	public boolean isTargetRootType() {
		return itType.getRoot();
	}
}
