package com.hiit.api.domain.usecase.it.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateInItEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void publish(Long inItId, Long memberId) {
		publisher.publishEvent(CreateInItEvent.builder().inItId(inItId).memberId(memberId).build());
	}
}
