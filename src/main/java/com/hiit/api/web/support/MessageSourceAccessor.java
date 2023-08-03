package com.hiit.api.web.support;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/** 메시지 소스 접근자 */
@Component
public class MessageSourceAccessor implements MessageSourceAware {

	private static org.springframework.context.support.MessageSourceAccessor messageSourceAccessor;

	/**
	 * 메시지 코드에 해당하는 메시지를 반환한다.
	 *
	 * @param code 메시지 코드
	 * @return 메시지
	 */
	public static String getMessage(final String code) {
		return messageSourceAccessor.getMessage(code);
	}

	/**
	 * 메시지 코드에 해당하는 메시지를 반환한다.
	 *
	 * @param code 메시지 코드
	 * @param args 메시지 파라미터
	 * @return 메시지
	 */
	public static String getMessage(final String code, final Object... args) {
		return messageSourceAccessor.getMessage(code, args);
	}

	/**
	 * 메시지 소스를 설정한다.
	 *
	 * @param messageSource message source to be used by this object
	 */
	@Override
	public void setMessageSource(final MessageSource messageSource) {
		messageSourceAccessor =
				new org.springframework.context.support.MessageSourceAccessor(
						messageSource, Locale.getDefault());
	}
}
