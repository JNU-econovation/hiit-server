package com.hiit.api.domain.model.hit;

import com.hiit.api.common.marker.model.AbstractDomain;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Hit implements HitGetId, AbstractDomain {

	private Long id;
	private Long withId;

	private HitterDetail hitter;
	private HitStatusDetails status;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public static Hit hit(final Long withId, final HitterDetail hitter) {
		return Hit.builder().withId(withId).hitter(hitter).status(HitStatusDetails.HIT).build();
	}

	public Hit miss() {
		return this.toBuilder().status(HitStatusDetails.MISS).build();
	}
}
