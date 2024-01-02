package com.hiit.api.domain.model.it.in;

import com.hiit.api.common.marker.model.AbstractDomain;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class InIt implements AbstractDomain {

	private Long id;
	private Long memberId;
	private Long itRelationId;

	private String title;
	private String resolution;
	private DayCodeInfo dayCode;
	private ItStatusInfo status;

	private InItTimeInfo timeInfo;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public boolean isOwner(Long memberId) {
		return this.memberId.equals(memberId);
	}
}
