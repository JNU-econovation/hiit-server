package com.hiit.api.web.controller.description;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class SupportDescription {

	public static FieldDescriptor[] banners() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("배너"),
			fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("배너 개수"),
			fieldWithPath("data.urls[]").type(JsonFieldType.ARRAY).description("배너 주소"),
		};
	}

	public static FieldDescriptor[] notice() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("알림"),
			fieldWithPath("data.notices[]").type(JsonFieldType.ARRAY).description("알림 리스트"),
			fieldWithPath("data.notices[].id").type(JsonFieldType.NUMBER).description("알림 id"),
			fieldWithPath("data.notices[].type").type(JsonFieldType.STRING).description("알림 type"),
			fieldWithPath("data.notices[].date").type(JsonFieldType.STRING).description("알림 날짜"),
			fieldWithPath("data.notices[].title").type(JsonFieldType.STRING).description("알림 제목"),
			fieldWithPath("data.notices[].content").type(JsonFieldType.STRING).description("알림 내용"),
		};
	}

	public static FieldDescriptor[] errors() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("에러 리스트"),
			fieldWithPath("data.errors[]").type(JsonFieldType.ARRAY).description("에러 리스트"),
			fieldWithPath("data.errors[].situation").type(JsonFieldType.STRING).description("에러 상황"),
			fieldWithPath("data.errors[].httpCode").type(JsonFieldType.NUMBER).description("에러 HTTP 코드"),
			fieldWithPath("data.errors[].code").type(JsonFieldType.STRING).description("에러 코드"),
		};
	}

	public static FieldDescriptor[] dayCode() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("날짜 코드 리스트"),
			fieldWithPath("data.days[]").type(JsonFieldType.ARRAY).description("날짜 코드 리스트"),
			fieldWithPath("data.days[].code").type(JsonFieldType.STRING).description("날짜 코드"),
			fieldWithPath("data.days[].day").type(JsonFieldType.STRING).description("날짜 코드 날짜"),
		};
	}

	public static FieldDescriptor[] type() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("날짜 코드 리스트"),
			fieldWithPath("data.socialSubjects[]").type(JsonFieldType.ARRAY).description("날짜 코드 리스트"),
			fieldWithPath("data.requestTypes[]").type(JsonFieldType.ARRAY).description("날짜 코드"),
		};
	}
}
