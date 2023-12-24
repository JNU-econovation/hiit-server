package com.hiit.api.domain.dto.response.with;

import com.hiit.api.common.marker.dto.response.ServiceResponse;
import com.hiit.api.web.support.Page;

public class WithPage extends Page<WithInfo> implements ServiceResponse {

	public WithPage(org.springframework.data.domain.Page<WithInfo> source) {
		super(source);
	}
}
