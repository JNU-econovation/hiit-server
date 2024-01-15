package com.hiit.api.domain.usecase.with.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteWithEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void publish(Long memberId, Long inItId, Long withId) {
		publisher.publishEvent(
				DeleteWithEvent.builder().memberId(memberId).inItId(inItId).withId(withId).build());
	}
}
