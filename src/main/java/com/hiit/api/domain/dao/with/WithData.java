package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.BaseData;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class WithData extends BaseData {

	private String content;
	private Long inItId;
}
