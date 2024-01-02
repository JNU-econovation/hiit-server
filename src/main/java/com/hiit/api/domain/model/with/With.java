package com.hiit.api.domain.model.with;

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
public class With implements AbstractDomain {

	private Long id;
	private Long inItId;
	private Long memberId;

	private String content;

	private WithItTimeInfo timeInfo;

	private LocalDateTime createAt;
	private LocalDateTime updateAt;
}
