package com.hiit.api.domain.support.entity.converter.hit;

import com.hiit.api.domain.model.hit.Hit;
import com.hiit.api.domain.model.hit.HitStatusDetails;
import com.hiit.api.domain.model.hit.HitterDetail;
import com.hiit.api.domain.usecase.hit.HitEntityConverter;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class HitEntityConverterImpl implements HitEntityConverter {

	@Override
	public Hit from(HitEntity entity) {
		Hitter hitter = entity.getHitter();
		if (hitter.getId().isEmpty()) {
			return getBaseHitData(entity).toBuilder().hitter(HitterDetail.anonymous()).build();
		}
		return getBaseHitData(entity).toBuilder().hitter(HitterDetail.of(hitter.getId().get())).build();
	}

	private Hit getBaseHitData(HitEntity entity) {
		Optional<Long> hitter = entity.getHitter().getId();
		HitterDetail hitterDetail = null;
		if (hitter.isEmpty()) {
			hitterDetail = HitterDetail.anonymous();
		} else {
			hitterDetail = HitterDetail.of(hitter.get());
		}
		return Hit.builder()
				.id(entity.getId())
				.withId(entity.getWithEntity().getId())
				.hitter(hitterDetail)
				.status(HitStatusDetails.valueOf(entity.getStatus().name()))
				.createAt(entity.getCreateAt())
				.updateAt(entity.getUpdateAt())
				.build();
	}

	public HitEntity to(Hit data) {
		HitterDetail hitter = data.getHitter();
		if (hitter.getId().isEmpty()) {
			return getBaseHitterEntity(data).toBuilder().hitter(Hitter.anonymous()).build();
		}
		return getBaseHitterEntity(data).toBuilder()
				.hitter(Hitter.builder().id(hitter.getId().get()).build())
				.build();
	}

	public HitEntity to(Long id, Hit data) {
		HitEntity entity = to(data);
		return entity.toBuilder().id(id).build();
	}

	private HitEntity getBaseHitterEntity(Hit data) {
		return HitEntity.builder()
				.status(HitStatus.valueOf(data.getStatus().name()))
				.withEntity(WithEntity.builder().id(data.getWithId()).build())
				.build();
	}
}
