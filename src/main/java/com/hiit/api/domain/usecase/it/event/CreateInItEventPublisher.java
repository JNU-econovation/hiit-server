package com.hiit.api.domain.usecase.it.event;

import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateInItEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void publish(Long inItId, Long memberId, ItTypeDetails type) {
		publisher.publishEvent(
				CreateInItEvent.builder().inItId(inItId).memberId(memberId).type(type).build());
	}
}
