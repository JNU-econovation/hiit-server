package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.BaseData;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class ItRelationData extends BaseData {

	private Long targetId;
	private TargetItTypeInfo targetType;
	private Long inItId;

	public boolean isType(TargetItTypeInfo targetType) {
		return targetType.equals(this.targetType);
	}

	public boolean isTarget(Long targetId) {
		return targetId.equals(this.targetId);
	}
}
