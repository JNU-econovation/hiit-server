package com.hiit.api.web.support.usecase;

import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.dto.PageableRequestImpl;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageRequestGenerator {

	public static PageRequest generate(Pageable pageable) {
		return new PageableRequestImpl(pageable);
	}
}
