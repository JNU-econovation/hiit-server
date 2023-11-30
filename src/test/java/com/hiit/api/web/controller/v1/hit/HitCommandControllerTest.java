package com.hiit.api.web.controller.v1.hit;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.dto.request.hit.HitRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = AppMain.class)
class HitCommandControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "Hit-Controller";
	private static final String BASE_URL = "/api/v1/its/withs/hits";

	private static final String HIT_BASE_ID = "HitInfo";

	@Test
	@DisplayName(BASE_URL)
	void hit() throws Exception {

		HitRequest request = HitRequest.builder().id(1L).build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL, 0)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								HIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("힛을 수행한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("HitRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("HitResponse"))
												.responseFields(
														Description.success(
																new FieldDescriptor[] {
																	fieldWithPath("data.count")
																			.type(JsonFieldType.NUMBER)
																			.description("힛 수")
																}))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void hit_invalidId() throws Exception {

		HitRequest request = HitRequest.builder().id(-1L).build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL, 0)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								HIT_BASE_ID + "invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("힛을 수행한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("HitRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("HitResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
