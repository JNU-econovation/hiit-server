package com.hiit.api.repository.entity.business.hit;

import com.hiit.api.repository.entity.BaseEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.util.Objects;
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
@Entity(name = "hit")
@Table(name = HitEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE hit_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class HitEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "hit_";

	@Enumerated(EnumType.STRING)
	private HitStatus status;

	@Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
			name = WithEntity.ENTITY_PREFIX + "fk",
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private WithEntity withEntity;

	/** memberId와 동일한 역활을 한다. */
	@Builder.Default
	@Convert(converter = HitterConverter.class)
	@Column(name = ENTITY_PREFIX + "hitter", nullable = false)
	private Hitter hitter = Hitter.anonymous();

	public HitEntity changeStatus() {
		if (Objects.equals(this.status, HitStatus.MISS)) {
			this.status = HitStatus.HIT;
			return this;
		}
		this.status = HitStatus.MISS;
		return this;
	}
}
