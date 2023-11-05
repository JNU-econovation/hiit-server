package com.hiit.api.web.controller.v1;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.FieldDescriptors;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.domain.dto.foo.response.FooUseCaseResponse;
import com.hiit.api.domain.usecase.foo.SaveFooUseCase;
import com.hiit.api.web.dto.request.SaveFooRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = AppMain.class)
class FooControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private SaveFooUseCase fooService;

	@Test
	void foo() throws Exception {
		SaveFooRequest request = SaveFooRequest.builder().name("foo").build();

		FooUseCaseResponse response = FooUseCaseResponse.builder().name("foo").build();

		when(fooService.execute(any(SaveFooRequest.class))).thenReturn(response);

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(post("/api/v1/foo", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"fooPost",
								resource(
										ResourceSnippetParameters.builder()
												.description("foo 서비스")
												.tag("foo")
												.requestSchema(Schema.schema("fooRequest"))
												.responseSchema(Schema.schema("fooResponse"))
												.responseFields(
														// todo 분리
														new FieldDescriptors(
																fieldWithPath("message")
																		.type(JsonFieldType.STRING)
																		.description("성공"),
																fieldWithPath("code")
																		.type(JsonFieldType.STRING)
																		.description("success"),
																fieldWithPath("data").type(JsonFieldType.OBJECT).description("foo"),
																fieldWithPath("data.name")
																		.type(JsonFieldType.STRING)
																		.description("foo 이름")))
												.build())));
	}
}
