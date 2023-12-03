package com.hiit.api.repository.init.it;

import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;

public class FakeInIt {

	public FakeInIt() {}

	public static InItEntity create(HiitMemberEntity member) {
		return InItEntity.builder()
				.title("title")
				.dayCode(DayCodeList.MON)
				.resolution("resolution")
				.hiitMember(member)
				.status(ItStatus.ACTIVE)
				.build();
	}

	public static InItEntity create(HiitMemberEntity member, DayCodeList dayCode) {
		return InItEntity.builder()
				.title("title")
				.dayCode(dayCode)
				.resolution("resolution")
				.hiitMember(member)
				.status(ItStatus.ACTIVE)
				.build();
	}
}
