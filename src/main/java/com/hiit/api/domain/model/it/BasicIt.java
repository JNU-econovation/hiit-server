package com.hiit.api.domain.model.it;

import com.hiit.api.common.marker.model.AbstractDomain;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/** 기본 IT */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BasicIt implements AbstractDomain {

	private Long id;
	private ItTypeDetails type;

	private String topic;
	private LocalTime startTime;
	private LocalTime endTime;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;
}
