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

	public static FieldDescriptor[] noti() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("알림"),
			fieldWithPath("data.noti[]").type(JsonFieldType.ARRAY).description("알림 리스트"),
			fieldWithPath("data.noti[].id").type(JsonFieldType.NUMBER).description("알림 id"),
			fieldWithPath("data.noti[].type").type(JsonFieldType.STRING).description("알림 type"),
			fieldWithPath("data.noti[].date").type(JsonFieldType.STRING).description("알림 날짜"),
			fieldWithPath("data.noti[].title").type(JsonFieldType.STRING).description("알림 제목"),
			fieldWithPath("data.noti[].content").type(JsonFieldType.STRING).description("알림 내용"),
		};
	}
}
