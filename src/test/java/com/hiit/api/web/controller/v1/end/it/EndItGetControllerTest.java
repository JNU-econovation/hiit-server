package com.hiit.api.web.controller.v1.end.it;

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
import com.hiit.api.web.controller.description.EndItDescription;
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
	private static final String TAG = "EndIt-Controller";
	private static final String BASE_URL = "/api/v1/end/its";

	@Test
	@DisplayName(BASE_URL + "/{id}")
	void browseEndIt() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{id}", 1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"EndItInfo",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇")
												.tag(TAG)
												.requestSchema(Schema.schema("EndItInfoRequest"))
												.pathParameters(parameterWithName("id").description("종료 잇 id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("EndItInfoResponse"))
												.responseFields(Description.success(EndItDescription.browseEndIt()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{id}")
	void browseEndIt_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{id}", -1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"EndItInfo_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇")
												.tag(TAG)
												.requestSchema(Schema.schema("EndItInfoRequest"))
												.pathParameters(parameterWithName("id").description("종료 잇 id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("EndItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void readEndIts() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL, 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"EndItInfos",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 목록")
												.tag(TAG)
												.requestSchema(Schema.schema("EndItInfosRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("EndItInfosResponse"))
												.responseFields(Description.success(EndItDescription.readEndIts()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/withs")
	void browseEndWith() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/withs", 0)
								.header("Authorization", "{{accessToken}}")
								.queryParam("id", "1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"EndWithInfo",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 윗")
												.tag(TAG)
												.requestSchema(Schema.schema("EndWithInfoRequest"))
												.requestParameters(parameterWithName("id").description("종료 잇 id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("EndWithInfoResponse"))
												.responseFields(Description.success(EndItDescription.browseEndWith()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/withs")
	void browseEndWith_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/withs", 0)
								.header("Authorization", "{{accessToken}}")
								.queryParam("id", "-1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"EndWithInfo_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 윗")
												.tag(TAG)
												.requestSchema(Schema.schema("EndWithInfoRequest"))
												.requestParameters(parameterWithName("id").description("종료 잇 id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("EndWithInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
