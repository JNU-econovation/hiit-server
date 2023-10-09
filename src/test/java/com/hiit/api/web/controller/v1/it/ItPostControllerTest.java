package com.hiit.api.web.controller.v1.it;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.ItDescription;
import com.hiit.api.web.dto.request.it.InItRequest;
import com.hiit.api.web.dto.request.it.ParticipateTogetherRequest;
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
	private static final String TAG = "It";
	private static final String BASE_URL = "/api/v1/its";

	@Test
	@DisplayName(BASE_URL + "/{id}/in")
	void inIt() throws Exception {

		InItRequest request = InItRequest.builder().dayCode(1L).build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/{id}/in", 1)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"InIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여")
												.tag(TAG)
												.requestSchema(Schema.schema("InItRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("InItResponse"))
												.responseFields(Description.success(ItDescription.withOutData()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{id}/together")
	void participateTogether() throws Exception {

		ParticipateTogetherRequest request =
				ParticipateTogetherRequest.builder().content("content").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/{id}/together", 1)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"ParticipateTogether",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 함께하기 작성")
												.tag(TAG)
												.requestSchema(Schema.schema("ParticipateTogetherRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("ParticipateTogetherResponse"))
												.responseFields(Description.success(ItDescription.withOutData()))
												.build())));
	}
}
