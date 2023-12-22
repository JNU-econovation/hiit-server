package com.hiit.api.web.controller.description;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class EndItDescription {

	public static FieldDescriptor[] browseEndIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("종료 잇"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("종료 잇 id"),
			fieldWithPath("data.title").type(JsonFieldType.STRING).description("종료 잇 제목"),
			fieldWithPath("data.topic").type(JsonFieldType.STRING).description("종료 잇 주제"),
			fieldWithPath("data.startTime").type(JsonFieldType.STRING).description("종료 잇 시작 시간"),
			fieldWithPath("data.endTime").type(JsonFieldType.STRING).description("종료 잇 종료 시간"),
			fieldWithPath("data.startDate").type(JsonFieldType.STRING).description("종료 잇 시작 날짜"),
			fieldWithPath("data.endDate").type(JsonFieldType.STRING).description("종료 잇 종료 날짜"),
			fieldWithPath("data.withCount").type(JsonFieldType.NUMBER).description("종료 잇 참여 횟수"),
		};
	}

	public static FieldDescriptor[] readEndIts() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("종료 잇"),
			fieldWithPath("data.endIts[]").type(JsonFieldType.ARRAY).description("종료 잇 리스트"),
			fieldWithPath("data.endIts[].id").type(JsonFieldType.NUMBER).description("종료 잇 id"),
			fieldWithPath("data.endIts[].title").type(JsonFieldType.STRING).description("종료 잇 제목"),
			fieldWithPath("data.endIts[].topic").type(JsonFieldType.STRING).description("종료 잇 주제"),
			fieldWithPath("data.endIts[].startTime").type(JsonFieldType.STRING).description("종료 잇 시작 시간"),
			fieldWithPath("data.endIts[].endTime").type(JsonFieldType.STRING).description("종료 잇 종료 시간"),
			fieldWithPath("data.endIts[].startDate").type(JsonFieldType.STRING).description("종료 잇 시작 날짜"),
			fieldWithPath("data.endIts[].endDate").type(JsonFieldType.STRING).description("종료 잇 종료 날짜"),
			fieldWithPath("data.endIts[].withCount").type(JsonFieldType.NUMBER).description("종료 잇 참여 횟수"),
		};
	}

	public static FieldDescriptor[] browseEndWith() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("종료 잇"),
			fieldWithPath("data.endWithInfos[]").type(JsonFieldType.ARRAY).description("종료 잇 리스트"),
			fieldWithPath("data.endWithInfos[].id").type(JsonFieldType.NUMBER).description("종료 잇 id"),
			fieldWithPath("data.endWithInfos[].content")
					.type(JsonFieldType.STRING)
					.description("종료 잇 내용"),
			fieldWithPath("data.endWithInfos[].hit").type(JsonFieldType.NUMBER).description("종료 잇 힛"),
			fieldWithPath("data.endWithInfos[].withMemberInfo")
					.type(JsonFieldType.OBJECT)
					.description("종료 잇 작성 멤버 정보"),
			fieldWithPath("data.endWithInfos[].withMemberInfo.id")
					.type(JsonFieldType.NUMBER)
					.description("종료 잇 작성 멤버 id"),
			fieldWithPath("data.endWithInfos[].withMemberInfo.profile")
					.type(JsonFieldType.STRING)
					.description("종료 잇 작성 멤버 프로필"),
			fieldWithPath("data.endWithInfos[].withMemberInfo.name")
					.type(JsonFieldType.STRING)
					.description("종료 잇 작성 멤버 이름"),
			fieldWithPath("data.endWithInfos[].withMemberInfo.resolution")
					.type(JsonFieldType.STRING)
					.description("종료 잇 작성 멤버 잇 다짐"),
		};
	}
}
