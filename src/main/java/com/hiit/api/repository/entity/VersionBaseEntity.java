package com.hiit.api.repository.entity;

import com.hiit.api.repository.support.listener.VersionUpdateListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@EntityListeners({VersionUpdateListener.class})
public class VersionBaseEntity extends BaseEntity {

	@Version
	@Column(name = "version")
	private Long version;

	public void update() {
		this.version += 1;
	}
}
