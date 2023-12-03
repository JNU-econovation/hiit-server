package com.hiit.api.repository.init.hit;

import com.hiit.api.repository.dao.bussiness.HitRepository;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.with.WithEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class HitInitializer {

	@Autowired HitRepository repository;

	private HitEntity data;

	public void initialize(WithEntity withEntity, Hitter hitter) {
		repository.deleteAll();
		this.setData(withEntity, hitter);
	}

	private HitEntity setData(WithEntity withEntity, Hitter hitter) {
		HitEntity source = FakeHit.create(withEntity, hitter);
		this.data = repository.save(source);
		return this.data;
	}

	public HitEntity getData() {
		return data;
	}
}
