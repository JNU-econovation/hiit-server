package com.hiit.api.domain.dao.bar;

import com.hiit.api.domain.dao.BaseData;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class BarData extends BaseData {

	private final String name;
	private final Long foo;
}
