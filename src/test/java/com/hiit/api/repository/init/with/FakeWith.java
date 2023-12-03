package com.hiit.api.repository.init.with;

import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;

public class FakeWith {

	public FakeWith() {}

	public static WithEntity create(InItEntity inIt) {
		return WithEntity.builder().content("tContent").inIt(inIt).build();
	}
}
