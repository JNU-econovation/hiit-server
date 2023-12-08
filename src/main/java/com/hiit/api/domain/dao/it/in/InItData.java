package com.hiit.api.domain.dao.it.in;

import com.hiit.api.domain.dao.BaseData;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class InItData extends BaseData {

	private String title;
	private String resolution;
	private DayCodeInfo dayCode;
	private ItStatusInfo status;
	private Long memberId;
	private Long itRelationId;
}
