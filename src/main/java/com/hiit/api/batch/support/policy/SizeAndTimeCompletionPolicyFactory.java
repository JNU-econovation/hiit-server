package com.hiit.api.batch.support.policy;

import lombok.experimental.UtilityClass;
import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy;

/** 실패 횟 수와 시간을 기준으로 CompletionPolicy를 생성하는 유틸리티 클래스 */
@UtilityClass
public class SizeAndTimeCompletionPolicyFactory {

	/**
	 * 실패 횟 수와 시간을 기준으로 CompletionPolicy를 생성한다.
	 *
	 * @param size 실패 횟 수
	 * @param time 시간
	 * @return CompletionPolicy 객체
	 */
	public static CompletionPolicy get(Integer size, Long time) {
		CompositeCompletionPolicy compositeCompletionPolicy = new CompositeCompletionPolicy();

		compositeCompletionPolicy.setPolicies(
				new CompletionPolicy[] {
					new SimpleCompletionPolicy(size), new TimeoutTerminationPolicy(time)
				});

		return compositeCompletionPolicy;
	}
}
