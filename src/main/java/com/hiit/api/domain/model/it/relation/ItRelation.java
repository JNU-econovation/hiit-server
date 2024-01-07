package com.hiit.api.domain.model.it.relation;

import com.hiit.api.common.marker.model.AbstractDomain;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ItRelation implements AbstractDomain {

	private Long id;
	private Long targetItId;
	private Long inItId;

	private TargetItTypeInfo targetItType;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public boolean isType(TargetItTypeInfo targetType) {
		return targetType.equals(this.targetItType);
	}

	public boolean isTarget(Long targetId) {
		return targetId.equals(this.targetItId);
	}

	public boolean isInIt(Long inItId) {
		return inItId.equals(this.inItId);
	}
}
