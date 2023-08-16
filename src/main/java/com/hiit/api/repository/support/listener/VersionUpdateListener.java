package com.hiit.api.repository.support.listener;

import com.hiit.api.repository.entity.VersionBaseEntity;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class VersionUpdateListener {

	@PreRemove
	private void preRemove(VersionBaseEntity entity) {
		entity.update();
	}

	@PreUpdate
	private void preUpdate(VersionBaseEntity entity) {
		entity.update();
	}
}
