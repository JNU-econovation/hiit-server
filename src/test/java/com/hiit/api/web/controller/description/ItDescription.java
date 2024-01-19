package com.hiit.api.web.controller.description;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class ItDescription {

	public static FieldDescriptor[] browseIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("잇 정보"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("잇 id"),
			fieldWithPath("data.topic").type(JsonFieldType.STRING).description("잇 주제"),
			fieldWithPath("data.startTime").type(JsonFieldType.STRING).description("잇 시작 시간"),
			fieldWithPath("data.endTime").type(JsonFieldType.STRING).description("잇 종료 시간"),
			fieldWithPath("data.inMemberCount").type(JsonFieldType.NUMBER).description("잇 참여 인원 수"),
			fieldWithPath("data.memberIn").type(JsonFieldType.BOOLEAN).description("잇 참여 여부"),
			fieldWithPath("data.type").type(JsonFieldType.STRING).description("잇 참여 타입"),
			fieldWithPath("data.inItId").type(JsonFieldType.NUMBER).description("참여 잇 아이디"),
		};
	}

	public static FieldDescriptor[] readIts() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("잇 리스트"),
			fieldWithPath("data.its").type(JsonFieldType.ARRAY).description("잇 리스트"),
			fieldWithPath("data.its[].id").type(JsonFieldType.NUMBER).description("잇 id"),
			fieldWithPath("data.its[].topic").type(JsonFieldType.STRING).description("잇 주제"),
			fieldWithPath("data.its[].startTime").type(JsonFieldType.STRING).description("잇 시작 시간"),
			fieldWithPath("data.its[].endTime").type(JsonFieldType.STRING).description("잇 종료 시간"),
			fieldWithPath("data.its[].inMemberCount").type(JsonFieldType.NUMBER).description("잇 참여 인원 수"),
			fieldWithPath("data.its[].memberIn").type(JsonFieldType.BOOLEAN).description("잇 참여 여부"),
			fieldWithPath("data.its[].type").type(JsonFieldType.STRING).description("잇 참여 타입"),
			fieldWithPath("data.its[].inItId").type(JsonFieldType.NUMBER).description("참여 잇 아이디"),
		};
	}

	public static FieldDescriptor[] readInIts() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("잇 참여 리스트"),
			fieldWithPath("data.itInInfos").type(JsonFieldType.ARRAY).description("잇 참여 리스트"),
			fieldWithPath("data.itInInfos[].id").type(JsonFieldType.NUMBER).description("잇 참여 id"),
			fieldWithPath("data.itInInfos[].title").type(JsonFieldType.STRING).description("잇 참여 제목"),
			fieldWithPath("data.itInInfos[].topic").type(JsonFieldType.STRING).description("잇 참여 주제"),
			fieldWithPath("data.itInInfos[].startTime")
					.type(JsonFieldType.STRING)
					.description("잇 참여 시작 시간"),
			fieldWithPath("data.itInInfos[].endTime")
					.type(JsonFieldType.STRING)
					.description("잇 참여 종료 시간"),
			fieldWithPath("data.itInInfos[].days").type(JsonFieldType.STRING).description("잇 참여 날짜 코드"),
			fieldWithPath("data.itInInfos[].inMemberCount")
					.type(JsonFieldType.NUMBER)
					.description("잇 참여 인원 수"),
			fieldWithPath("data.itInInfos[].type").type(JsonFieldType.STRING).description("잇 참여 타입"),
		};
	}

	public static FieldDescriptor[] browseInIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("잇 참여 정보"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("잇 참여 id"),
			fieldWithPath("data.title").type(JsonFieldType.STRING).description("잇 참여 제목"),
			fieldWithPath("data.topic").type(JsonFieldType.STRING).description("잇 참여 주제"),
			fieldWithPath("data.startTime").type(JsonFieldType.STRING).description("잇 참여 시작 시간"),
			fieldWithPath("data.endTime").type(JsonFieldType.STRING).description("잇 참여 종료 시간"),
			fieldWithPath("data.days").type(JsonFieldType.STRING).description("잇 참여 날짜 코드"),
			fieldWithPath("data.inMemberCount").type(JsonFieldType.NUMBER).description("잇 참여 인원 수"),
			fieldWithPath("data.type").type(JsonFieldType.STRING).description("잇 참여 타입"),
		};
	}

	public static FieldDescriptor[] readItMotivations() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("잇 참여 동기부여 정보"),
			fieldWithPath("data.motivations").type(JsonFieldType.ARRAY).description("잇 참여 동기부여"),
		};
	}
}
