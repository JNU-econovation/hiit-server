package com.hiit.api.repository.entity.business;

import com.hiit.api.repository.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@SuperBuilder(toBuilder = true)
@Entity(name = "foo")
@Table(name = FooEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE foo_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class FooEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "foo_";

	@Column(name = ENTITY_PREFIX + "name", nullable = false, length = 100)
	private String name;

	private Long count;

	@Exclude
	@Default
	@OneToMany(
			mappedBy = "foo",
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	@OrderBy(value = "id desc")
	@BatchSize(size = 3)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<BarEntity> bars = new ArrayList<>();
}
