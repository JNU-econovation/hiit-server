package com.hiit.api.web.controller.v1.it;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.dto.request.it.AddInItRequest;
import com.hiit.api.web.dto.request.it.DeleteInItRequest;
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
class ItPostControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "It-Controller";
	private static final String BASE_URL = "/api/v1/its";

	@Test
	@DisplayName(BASE_URL + "/ins")
	void addInIt() throws Exception {

		AddInItRequest request =
				AddInItRequest.builder()
						.id(1L)
						.dayCode(Long.toBinaryString(000001L))
						.resolution("다짐")
						.build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/ins", 0)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"AddInIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddInItRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("AddInItResponse"))
												.responseFields(Description.created())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/ins")
	void editInIt() throws Exception {

		AddInItRequest request =
				AddInItRequest.builder()
						.id(1L)
						.dayCode(Long.toBinaryString(000001L))
						.resolution("다짐")
						.build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						put(BASE_URL + "/ins", 0)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"EditInIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여 정보를 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditInItRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("EditInItResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/ins")
	void deleteInIt() throws Exception {

		DeleteInItRequest request = DeleteInItRequest.builder().id(1L).endTitle("종료 잇 제목").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						delete(BASE_URL + "/ins", 0)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"DeleteInIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여를 종료한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteInItRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("DeleteInItResponse"))
												.responseFields(Description.success())
												.build())));
	}
}
