package com.hiit.api.domain.usecase.member.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateMemberEventPublisher {

	private final ApplicationEventPublisher publisher;

	public void publish(Long memberId) {
		publisher.publishEvent(CreateMemberEvent.builder().memberId(memberId).build());
	}
}
