package com.hiit.api.repository.entity.business.it;

import com.hiit.api.repository.entity.BaseEntity;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "registered_it")
@Table(name = RegisteredItEntity.ENTITY_PREFIX + "tb")
@SQLDelete(sql = "UPDATE registered_it_tb SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class RegisteredItEntity extends BaseEntity {

	public static final String ENTITY_PREFIX = "registered_it_";

	@Column(name = ENTITY_PREFIX + "topic", nullable = false)
	private String topic;

	@Column(name = ENTITY_PREFIX + "start_time", nullable = false)
	private LocalTime startTime;

	@Column(name = ENTITY_PREFIX + "end_time", nullable = false)
	private LocalTime endTime;
}
