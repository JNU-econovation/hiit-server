package com.hiit.api.repository.document;

import com.hiit.api.repository.support.listener.DocumentListener;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@MappedSuperclass
@EntityListeners({DocumentListener.class, AuditingEntityListener.class})
@SuperBuilder(toBuilder = true)
public class StringIdBaseEntity implements Persistable<String> {

	@Id private String id;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createAt;

	@Column(nullable = false)
	@LastModifiedDate
	private LocalDateTime updateAt;

	@Override
	public boolean isNew() {
		return createAt == null && updateAt == null;
	}

	public void setId(String id) {
		this.id = id;
	}
}
