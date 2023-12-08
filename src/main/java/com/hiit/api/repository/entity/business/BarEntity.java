package com.hiit.api.repository.entity.business;

import com.hiit.api.repository.entity.VersionBaseEntity;
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
@SuperBuilder(toBuilder = true)
@ToString
@Entity(name = "bar")
@Table(name = BarEntity.ENTITY_PREFIX + "tb")
@SQLDelete(
		sql = "UPDATE bar_tb SET deleted=true , version = version + 1 WHERE id = ? AND version = ?")
@Where(clause = "deleted=false")
public class BarEntity extends VersionBaseEntity {

	public static final String ENTITY_PREFIX = "bar_";

	@Column(name = ENTITY_PREFIX + "name", nullable = false, length = 100)
	private String name;

	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = FooEntity.ENTITY_PREFIX + "fk",
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private FooEntity foo;
}
