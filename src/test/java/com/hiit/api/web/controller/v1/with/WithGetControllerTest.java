package com.hiit.api.web.controller.v1.with;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.WithDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = AppMain.class)
class WithGetControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "With-Controller";
	private static final String BASE_URL = "/api/v1/its/withs";

	private static final String WITH_BASE_ID = "WithInfo";

	@Test
	@DisplayName(BASE_URL)
	void readWiths() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL, 0)
								.queryParam("page", "0")
								.queryParam("size", "10")
								.queryParam("sort", "id,desc")
								.queryParam("id", "1")
								.queryParam("my", "true")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								WITH_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 정보 조회를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("WithInfoRequest"))
												.requestParameters(
														parameterWithName("id").description("윗 id"),
														parameterWithName("my").description("나의 윗 정보 조회 여부"),
														parameterWithName("page").description("페이지 번호"),
														parameterWithName("size").description("페이지 크기"),
														parameterWithName("sort").description("정렬 조건"))
												.responseSchema(Schema.schema("WithInfoResponse"))
												.responseFields(Description.success(WithDescription.readWiths()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void readWiths_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL, 0)
								.queryParam("page", "0")
								.queryParam("size", "10")
								.queryParam("sort", "id,desc")
								.queryParam("id", "-1")
								.queryParam("my", "true")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								WITH_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 정보 조회를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("WithInfoRequest"))
												.requestParameters(
														parameterWithName("id").description("윗 id"),
														parameterWithName("my").description("나의 윗 정보 조회 여부"),
														parameterWithName("page").description("페이지 번호"),
														parameterWithName("size").description("페이지 크기"),
														parameterWithName("sort").description("정렬 조건"))
												.responseSchema(Schema.schema("WithInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
