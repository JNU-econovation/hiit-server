package com.hiit.api.domain.dto.response.with;

import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.support.Page;

public class WithPage extends Page<WithInfo> implements AbstractResponse {

	public WithPage(org.springframework.data.domain.Page<WithInfo> source) {
		super(source);
	}
}
