package com.hiit.api.repository.init.hit;

import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.with.WithEntity;

public class FakeHit {

	public FakeHit() {}

	public static HitEntity create(WithEntity withEntity, Hitter hitter) {
		return HitEntity.builder().status(HitStatus.HIT).withEntity(withEntity).hitter(hitter).build();
	}
}
