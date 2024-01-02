package com.hiit.api.repository.init.with;

import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;

public class FakeWith {

	public FakeWith() {}

	public static WithEntity create(InItEntity inIt, HiitMemberEntity hiitMember) {
		return WithEntity.builder().content("tContent").inIt(inIt).memberId(hiitMember.getId()).build();
	}
}
