package com.hiit.api.domain.dao.hit;

import com.hiit.api.domain.dao.BaseData;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class HitData extends BaseData {

	private HitStatusInfo status;
	private Long withId;
	private HitterInfo hitter;

	public static HitData hit(Long withId, HitterInfo hitter) {
		return HitData.builder().withId(withId).hitter(hitter).status(HitStatusInfo.HIT).build();
	}

	public static HitData miss(Long withId, HitterInfo hitter) {
		return HitData.builder().withId(withId).hitter(hitter).status(HitStatusInfo.MISS).build();
	}
}
