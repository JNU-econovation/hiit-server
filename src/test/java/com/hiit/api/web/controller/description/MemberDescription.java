package com.hiit.api.web.controller.description;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class MemberDescription {

	public static FieldDescriptor[] browseMember() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("멤버"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("멤버 id"),
			fieldWithPath("data.name").type(JsonFieldType.STRING).description("멤버 이름"),
			fieldWithPath("data.profile").type(JsonFieldType.STRING).description("멤버 프로필"),
			fieldWithPath("data.inItCount").type(JsonFieldType.NUMBER).description("멤버 잇 참여 수"),
		};
	}

	public static FieldDescriptor[] browseItStat() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("멤버"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("멤버 id"),
			fieldWithPath("data.name").type(JsonFieldType.STRING).description("멤버 이름"),
			fieldWithPath("data.profile").type(JsonFieldType.STRING).description("멤버 프로필"),
			fieldWithPath("data.withCount").type(JsonFieldType.NUMBER).description("멤버 윗 수"),
		};
	}
}
