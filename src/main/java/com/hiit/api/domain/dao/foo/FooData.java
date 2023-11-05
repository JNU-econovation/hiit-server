package com.hiit.api.domain.dao.foo;

import com.hiit.api.domain.dao.BaseData;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class FooData extends BaseData {

	private final String name;
	private final List<Long> bars;
}
