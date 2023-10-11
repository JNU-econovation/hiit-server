package com.hiit.api.web.controller.v1.it;

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
import com.hiit.api.web.controller.description.ItDescription;
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
class EndItGetControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "It";
	private static final String BASE_URL = "/api/v1/end/its";

	@Test
	@DisplayName(BASE_URL)
	void browseIt() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL, 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"BrowseEndIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("멤버가 종료한 잇 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseEndItRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseEndItResponse"))
												.responseFields(Description.success(ItDescription.browseEndIt()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{id}")
	void readIt() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{id}", 1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"ReadEndIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇이 주제 정보")
												.tag(TAG)
												.requestSchema(Schema.schema("ReadEndItRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("ReadEndItResponse"))
												.responseFields(Description.success(ItDescription.readEndIt()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{id}/together/my")
	void browseMyTogether() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{id}/together/my", 1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"BrowseEndMyTogether",
								resource(
										ResourceSnippetParameters.builder()
												.description("나의 종료 잇 함께하기 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseEndMyTogetherRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseEndMyTogetherResponse"))
												.responseFields(Description.success(ItDescription.browseMyTogether()))
												.build())));
	}
}
