package com.hiit.api.web.controller.description;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class MemberDescription {

	public static FieldDescriptor[] withOutData() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.NULL).description("null"),
		};
	}

	public static FieldDescriptor[] browseFriend() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("친구"),
			fieldWithPath("data.members").type(JsonFieldType.ARRAY).description("친구 리스트"),
			fieldWithPath("data.members[].id").type(JsonFieldType.NUMBER).description("친구 id"),
			fieldWithPath("data.members[].name").type(JsonFieldType.STRING).description("친구 이름"),
			fieldWithPath("data.members[].picture").type(JsonFieldType.STRING).description("친구 사진"),
			fieldWithPath("data.members[].itCommonCount")
					.type(JsonFieldType.NUMBER)
					.description("친구 공통 잇 수"),
		};
	}

	public static FieldDescriptor[] mypage() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("멤버"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("멤버 id"),
			fieldWithPath("data.name").type(JsonFieldType.STRING).description("멤버 이름"),
			fieldWithPath("data.comment").type(JsonFieldType.STRING).description("멤버 다짐"),
			fieldWithPath("data.picture").type(JsonFieldType.STRING).description("멤버 사진 url"),
			fieldWithPath("data.friendCount").type(JsonFieldType.NUMBER).description("멤버 친구 수"),
			fieldWithPath("data.itInCount").type(JsonFieldType.NUMBER).description("참여 잇 수"),
		};
	}

	public static FieldDescriptor[] read() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("멤버"),
			fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("멤버 id"),
			fieldWithPath("data.name").type(JsonFieldType.STRING).description("멤버 이름"),
			fieldWithPath("data.comment").type(JsonFieldType.STRING).description("멤버 다짐"),
			fieldWithPath("data.picture").type(JsonFieldType.STRING).description("멤버 사진 url"),
			fieldWithPath("data.friendStatus").type(JsonFieldType.BOOLEAN).description("멤버 친구 여부"),
			fieldWithPath("data.banStatus").type(JsonFieldType.BOOLEAN).description("멤버 차단 여부"),
		};
	}

	public static FieldDescriptor[] browseCommonIts() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("공통 참여 잇"),
			fieldWithPath("data.its").type(JsonFieldType.ARRAY).description("공통 참여 잇"),
			fieldWithPath("data.its[].id").type(JsonFieldType.NUMBER).description("공통 참여 잇 id"),
			fieldWithPath("data.its[].topic").type(JsonFieldType.STRING).description("공통 참여 잇 주제"),
			fieldWithPath("data.its[].startTime").type(JsonFieldType.NUMBER).description("공통 참여 잇 시작시간"),
			fieldWithPath("data.its[].endTime").type(JsonFieldType.NUMBER).description("공통 참여 잇 종료시간"),
			fieldWithPath("data.its[].commonDays")
					.type(JsonFieldType.NUMBER)
					.description("공통 참여 잇 공통 요일 코드"),
			fieldWithPath("data.its[].notiStatus")
					.type(JsonFieldType.BOOLEAN)
					.description("공통 참여 잇 알림 여부"),
		};
	}

	public static FieldDescriptor[] readMemberItCommonTogether() {
		return new FieldDescriptor[] {
			fieldWithPath("data").type(JsonFieldType.OBJECT).description("멤버의 잇 함께하기 정보"),
			fieldWithPath("data.participateCount").type(JsonFieldType.NUMBER).description("잇 참여 횟수"),
			fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("멤버 id"),
			fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("멤버 이름"),
			fieldWithPath("data.memberPicture").type(JsonFieldType.STRING).description("멤버 사진 url"),
		};
	}
}
