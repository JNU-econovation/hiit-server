package com.hiit.api.repository.entity.business.with;

import com.hiit.api.repository.entity.BaseEntity;
import com.hiit.api.repository.entity.business.it.InItEntity;
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
}
