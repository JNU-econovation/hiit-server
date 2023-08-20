package com.hiit.api.repository.entity.log;

import com.hiit.api.repository.entity.QueueLogBaseEntity;
import com.hiit.api.repository.entity.business.FooEntity;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@SuperBuilder(toBuilder = true)
@Entity(name = "fooQueueLog")
@Table(name = FooQueueLogEntity.ENTITY_PREFIX + "tb")
public class FooQueueLogEntity extends QueueLogBaseEntity {

	public static final String ENTITY_PREFIX = "fooQueueLog_";

	@ManyToOne
	@JoinColumn(
			name = FooEntity.ENTITY_PREFIX + "refId",
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private FooEntity editId;

	@Default
	@Enumerated(value = EnumType.STRING)
	@Column(name = ENTITY_PREFIX + "status", nullable = false)
	private QueueStatus status = QueueStatus.READY;

	public void done() {
		this.status = QueueStatus.DONE;
	}
}
