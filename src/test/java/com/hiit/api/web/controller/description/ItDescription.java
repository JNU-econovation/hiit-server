package com.hiit.api.web.controller.description;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class ItDescription {

	public static FieldDescriptor[] withOutData() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.NULL).description("null"),
		};
	}

	public static FieldDescriptor[] browseIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("it 리스트"),
			fieldWithPath("data.its").type(JsonFieldType.ARRAY).description("it 리스트"),
			fieldWithPath("data.its[].id").type(JsonFieldType.NUMBER).description("it id"),
			fieldWithPath("data.its[].topic").type(JsonFieldType.STRING).description("it 주제"),
			fieldWithPath("data.its[].startTime").type(JsonFieldType.NUMBER).description("it 시작 시간"),
			fieldWithPath("data.its[].endTime").type(JsonFieldType.NUMBER).description("it 종료 시간"),
			fieldWithPath("data.its[].participatePerson")
					.type(JsonFieldType.NUMBER)
					.description("it 참여 인원"),
			fieldWithPath("data.its[].memberIn").type(JsonFieldType.BOOLEAN).description("it 참여 여부"),
		};
	}

	public static FieldDescriptor[] browseEndIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("it 리스트"),
			fieldWithPath("data.its").type(JsonFieldType.ARRAY).description("it 리스트"),
			fieldWithPath("data.its[].id").type(JsonFieldType.NUMBER).description("it id"),
			fieldWithPath("data.its[].topic").type(JsonFieldType.STRING).description("it 주제"),
			fieldWithPath("data.its[].day").type(JsonFieldType.NUMBER).description("it 요일 코드"),
			fieldWithPath("data.its[].period").type(JsonFieldType.STRING).description("it 기간"),
			fieldWithPath("data.its[].startTime").type(JsonFieldType.NUMBER).description("it 시작 시간"),
			fieldWithPath("data.its[].endTime").type(JsonFieldType.NUMBER).description("it 종료 시간"),
			fieldWithPath("data.its[].participateCount")
					.type(JsonFieldType.NUMBER)
					.description("it 참여 횟수"),
		};
	}

	public static FieldDescriptor[] browseInIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("it 리스트"),
			fieldWithPath("data.its").type(JsonFieldType.ARRAY).description("it 리스트"),
			fieldWithPath("data.its[].id").type(JsonFieldType.NUMBER).description("it id"),
			fieldWithPath("data.its[].topic").type(JsonFieldType.STRING).description("it 주제"),
			fieldWithPath("data.its[].startTime").type(JsonFieldType.NUMBER).description("it 시작 시간"),
			fieldWithPath("data.its[].endTime").type(JsonFieldType.NUMBER).description("it 종료 시간"),
			fieldWithPath("data.its[].participatePerson")
					.type(JsonFieldType.NUMBER)
					.description("it 참여 인원"),
			fieldWithPath("data.its[].day").type(JsonFieldType.NUMBER).description("it 참여 날짝 코드"),
		};
	}

	public static FieldDescriptor[] readIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("it"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("it id"),
			fieldWithPath("data.topic").type(JsonFieldType.STRING).description("it 주제"),
			fieldWithPath("data.startTime").type(JsonFieldType.NUMBER).description("it 시작 시간"),
			fieldWithPath("data.endTime").type(JsonFieldType.NUMBER).description("it 종료 시간"),
			fieldWithPath("data.participatePerson").type(JsonFieldType.NUMBER).description("it 참여 인원"),
		};
	}

	public static FieldDescriptor[] readEndIt() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("it"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("it id"),
			fieldWithPath("data.topic").type(JsonFieldType.STRING).description("it 주제"),
			fieldWithPath("data.day").type(JsonFieldType.NUMBER).description("it 요일 코드"),
			fieldWithPath("data.period").type(JsonFieldType.STRING).description("it 기간"),
			fieldWithPath("data.startTime").type(JsonFieldType.NUMBER).description("it 시작 시간"),
			fieldWithPath("data.endTime").type(JsonFieldType.NUMBER).description("it 종료 시간"),
			fieldWithPath("data.participateCount").type(JsonFieldType.NUMBER).description("it 참여 횟수"),
		};
	}

	public static FieldDescriptor[] browseTogether() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("together 리스트"),
			fieldWithPath("data.togethers").type(JsonFieldType.ARRAY).description("together 리스트"),
			fieldWithPath("data.togethers[].id").type(JsonFieldType.NUMBER).description("together id"),
			fieldWithPath("data.togethers[].content")
					.type(JsonFieldType.STRING)
					.description("together 내용"),
			fieldWithPath("data.togethers[].memberName")
					.type(JsonFieldType.STRING)
					.description("together 멤버 이름"),
			fieldWithPath("data.togethers[].memberPicture")
					.type(JsonFieldType.STRING)
					.description("together 멤버 사진"),
			fieldWithPath("data.togethers[].memberComment")
					.type(JsonFieldType.STRING)
					.description("together 멤버 다짐"),
			fieldWithPath("data.togethers[].hits").type(JsonFieldType.NUMBER).description("together 힛 수"),
		};
	}

	public static FieldDescriptor[] browseMyTogether() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("together 리스트"),
			fieldWithPath("data.togethers").type(JsonFieldType.ARRAY).description("together 리스트"),
			fieldWithPath("data.togethers[].id").type(JsonFieldType.NUMBER).description("together id"),
			fieldWithPath("data.togethers[].content")
					.type(JsonFieldType.STRING)
					.description("together 내용"),
			fieldWithPath("data.togethers[].date")
					.type(JsonFieldType.NUMBER)
					.description("together 날짜 코드"),
			fieldWithPath("data.togethers[].hits").type(JsonFieldType.NUMBER).description("together 힛 수"),
		};
	}

	public static FieldDescriptor[] browseRankTogether() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("together 리스트"),
			fieldWithPath("data.togethers").type(JsonFieldType.ARRAY).description("together 리스트"),
			fieldWithPath("data.togethers[].id").type(JsonFieldType.NUMBER).description("together id"),
			fieldWithPath("data.togethers[].content")
					.type(JsonFieldType.STRING)
					.description("together 내용"),
			fieldWithPath("data.togethers[].date")
					.type(JsonFieldType.NUMBER)
					.description("together 날짜 코드"),
			fieldWithPath("data.togethers[].hits").type(JsonFieldType.NUMBER).description("together 힛 수"),
		};
	}
}
