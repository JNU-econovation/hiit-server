package com.hiit.api.domain.service.with;

import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.with.GetWithId;
import com.hiit.api.domain.model.with.With;

public interface WithTimeDetailsQuery {

	With query(GetWithId withId, GetMemberId memberId);

	InItStatusDetails getStatus();
}
