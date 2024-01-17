package com.hiit.api.domain.model.it.in;

import com.hiit.api.common.marker.model.AbstractDomain;
import com.hiit.api.domain.model.member.GetMemberId;
import java.time.LocalDateTime;
import java.util.Objects;
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
	private Long itId;
	private String itType;

	private String title;
	private String resolution;
	private DayCodeDetails dayCode;
	private InItStatusDetails status;

	private InItTimeDetails time;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;

	public boolean isOwner(final GetMemberId memberId) {
		return Objects.equals(this.memberId, memberId.getId());
	}

	public void updateTitle(final String title) {
		this.title = title;
	}

	public void updateResolution(final String resolution) {
		this.resolution = resolution;
	}

	public void updateDayCode(final DayCodeDetails dayCode) {
		this.dayCode = dayCode;
	}

	public void end() {
		this.status = InItStatusDetails.END;
	}
}
