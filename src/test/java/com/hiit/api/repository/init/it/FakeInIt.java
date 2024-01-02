package com.hiit.api.repository.init.it;

import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.time.LocalTime;

public class FakeInIt {

	public FakeInIt() {}

	public static InItEntity create(HiitMemberEntity member) {
		LocalTime now = LocalTime.now();
		LocalTime startTime = now.minusHours(1);
		LocalTime endTime = now.plusHours(1);
		String info = "{\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\"}";
		return InItEntity.builder()
				.title("title")
				.dayCode(DayCodeList.MON)
				.resolution("resolution")
				.hiitMember(member)
				.info(info)
				.status(ItStatus.ACTIVE)
				.build();
	}

	public static InItEntity create(HiitMemberEntity member, DayCodeList dayCode) {
		LocalTime now = LocalTime.now();
		LocalTime startTime = now.minusHours(1);
		LocalTime endTime = now.plusHours(1);
		String info = "{\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\"}";
		return InItEntity.builder()
				.title("title")
				.dayCode(dayCode)
				.resolution("resolution")
				.hiitMember(member)
				.info(info)
				.status(ItStatus.ACTIVE)
				.build();
	}
}
