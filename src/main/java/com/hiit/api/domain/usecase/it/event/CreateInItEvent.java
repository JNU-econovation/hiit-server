package com.hiit.api.domain.usecase.it.event;

import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CreateInItEvent {

	private Long memberId;
	private Long inItId;
	private ItTypeDetails type;
}
