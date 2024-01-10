package com.hiit.api.domain.service.with;

import com.hiit.api.domain.model.with.GetWithId;
import com.hiit.api.domain.model.with.With;

public interface WithQuery {

	With query(GetWithId withId);
}
