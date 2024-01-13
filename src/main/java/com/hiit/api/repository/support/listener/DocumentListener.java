package com.hiit.api.repository.support.listener;

import com.hiit.api.repository.document.StringIdBaseEntity;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.PrePersist;

public class DocumentListener {

	@PrePersist
	private void prePersist(StringIdBaseEntity entity) {
		if (!Objects.isNull(entity.getId())) {
			return;
		}
		entity.setId(UUID.randomUUID().toString());
	}
}
