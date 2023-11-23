package com.hiit.api.web.controller.description;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class WithDescription {

	public static FieldDescriptor[] readWiths() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("윗 페이지"),
			fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("윗 페이지 크기"),
			fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("윗 페이지 번호"),
			fieldWithPath("data.totalPageCount").type(JsonFieldType.NUMBER).description("윗 전체 페이지 수"),
			fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("윗 전체 수"),
			fieldWithPath("data.data[]").type(JsonFieldType.ARRAY).description("윗"),
			fieldWithPath("data.data[].id").type(JsonFieldType.NUMBER).description("윗 id"),
			fieldWithPath("data.data[].content").type(JsonFieldType.STRING).description("윗 내용"),
			fieldWithPath("data.data[].hit").type(JsonFieldType.NUMBER).description("윗의 힛"),
			fieldWithPath("data.data[].withMemberInfo").type(JsonFieldType.OBJECT).description("윗 멤버 정보"),
			fieldWithPath("data.data[].withMemberInfo.profile")
					.type(JsonFieldType.STRING)
					.description("윗 멤버 프로필"),
			fieldWithPath("data.data[].withMemberInfo.name")
					.type(JsonFieldType.STRING)
					.description("윗 멤버 이름"),
			fieldWithPath("data.data[].withMemberInfo.resolution")
					.type(JsonFieldType.STRING)
					.description("윗 멤버 다짐"),
		};
	}
}
