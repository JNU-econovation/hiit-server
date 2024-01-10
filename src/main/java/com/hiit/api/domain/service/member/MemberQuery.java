package com.hiit.api.domain.service.member;

import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.member.Member;

public interface MemberQuery {

	Member query(GetMemberId memberId);
}
