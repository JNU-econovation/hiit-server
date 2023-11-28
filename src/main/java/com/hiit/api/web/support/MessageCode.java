package com.hiit.api.web.support;

/** 메시지 코드 */
public enum MessageCode {
	/** 성공 메시지 코드 */
	SUCCESS("success", "성공"),
	/** 삭제 메시지 코드 */
	RESOURCE_DELETED("resource.deleted", "삭제되었습니다."),
	/** 수정 메시지 코드 */
	RESOURCE_UPDATED("resource.updated", "수정되었습니다."),
	/** 생성 메시지 코드 */
	RESOURCE_CREATED("resource.created", "새로 생성되었습니다.");
	private final String code;
	private final String value;

	MessageCode(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return this.code;
	}

	public String getValue() {
		return this.value;
	}
}
