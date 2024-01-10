package com.hiit.api.domain.service.it.in;

import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.member.GetMemberId;

public interface InItTimeDetailsQuery {

	InIt query(GetInItId inItId, GetMemberId memberId);

	InItStatusDetails getStatus();
}
