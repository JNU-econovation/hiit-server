package com.hiit.api.web.controller.description;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class Description {

	private static FieldDescriptor getSuccessCodeDescriptor() {
		return fieldWithPath("code").type(JsonFieldType.STRING).description("success");
	}

	private static FieldDescriptor getSuccessMessageDescriptor() {
		return fieldWithPath("message").type(JsonFieldType.STRING).description("성공");
	}

	public static FieldDescriptor[] success(FieldDescriptor[] data) {
		return ArrayUtils.addAll(data, getSuccessMessageDescriptor(), getSuccessCodeDescriptor());
	}

	public static FieldDescriptor[] success() {
		return new FieldDescriptor[] {getSuccessMessageDescriptor(), getSuccessCodeDescriptor()};
	}

	private static FieldDescriptor getCreateCodeDescriptor() {
		return fieldWithPath("code").type(JsonFieldType.STRING).description("resource.created");
	}

	private static FieldDescriptor getCreateMessageDescriptor() {
		return fieldWithPath("message").type(JsonFieldType.STRING).description("새로 생성되었습니다.");
	}

	public static FieldDescriptor[] created() {
		return new FieldDescriptor[] {getCreateCodeDescriptor(), getCreateMessageDescriptor()};
	}

	public static HeaderDescriptorWithType authHeader() {
		return headerWithName("Authorization")
				.defaultValue("{{accessToken}}")
				.description("Bearer 어세스 토큰");
	}
}