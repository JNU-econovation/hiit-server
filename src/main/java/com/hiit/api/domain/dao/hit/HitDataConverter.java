package com.hiit.api.domain.dao.hit;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.with.WithEntity;
import org.springframework.stereotype.Component;

@Component
public class HitDataConverter implements AbstractDataConverter<HitEntity, HitData> {

	@Override
	public HitData from(HitEntity entity) {
		Hitter hitter = entity.getHitter();
		if (hitter.getId().isEmpty()) {
			return getBaseHitData(entity).toBuilder().hitter(HitterInfo.anonymous()).build();
		}
		return getBaseHitData(entity).toBuilder().hitter(HitterInfo.of(hitter.getId().get())).build();
	}

	private HitData getBaseHitData(HitEntity entity) {
		return HitData.builder()
				.status(HitStatusInfo.valueOf(entity.getStatus().name()))
				.withId(entity.getWithEntity().getId())
				.build();
	}

	public HitEntity to(HitData data) {
		HitterInfo hitter = data.getHitter();
		if (hitter.getId().isEmpty()) {
			return getBaseHitterEntity(data).toBuilder().hitter(Hitter.anonymous()).build();
		}
		return getBaseHitterEntity(data).toBuilder()
				.hitter(Hitter.builder().id(hitter.getId().get()).build())
				.build();
	}

	private HitEntity getBaseHitterEntity(HitData data) {
		return HitEntity.builder()
				.status(HitStatus.valueOf(data.getStatus().name()))
				.withEntity(WithEntity.builder().id(data.getWithId()).build())
				.build();
	}
}
