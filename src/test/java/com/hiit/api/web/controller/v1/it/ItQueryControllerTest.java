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
class ItQueryControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "It-Controller";
	private static final String BASE_URL = "/api/v1/its";

	private static final String IT_BASE_ID = "ItInfo";

	@Test
	@DisplayName(BASE_URL + "/{id}")
	void browseIt() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								IT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItInfoRequest"))
												.pathParameters(parameterWithName("id").description("잇 id"))
												.responseSchema(Schema.schema("ItInfoResponse"))
												.responseFields(Description.success(ItDescription.browseIt()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{id}")
	void browseIt_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/{id}", -1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								IT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItInfoRequest"))
												.pathParameters(parameterWithName("id").description("잇 id"))
												.responseSchema(Schema.schema("ItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void readIts() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL, 0).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								IT_BASE_ID + "s",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 목록을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItInfosRequest"))
												.responseSchema(Schema.schema("ItInfosResponse"))
												.responseFields(Description.success(ItDescription.readIts()))
												.build())));
	}

	private static final String INIT_BASE_ID = "InItInfo";

	@Test
	@DisplayName(BASE_URL + "/ins")
	void readInIts() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/ins", 0).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								INIT_BASE_ID + "s",
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇 목록을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("InItInfoRequest"))
												.responseSchema(Schema.schema("InItInfoResponse"))
												.responseFields(Description.success(ItDescription.readInIts()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/ins/{id}")
	void browseInIt() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/ins/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								INIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("InItInfoRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("InItInfoResponse"))
												.responseFields(Description.success(ItDescription.browseInIt()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/ins/{id}")
	void browseInIt_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/ins/{id}", -1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								INIT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("InItInfoRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("InItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	private static final String IT_MOTIVATIONS_BASE_ID = "ItMotivations";

	@Test
	@DisplayName(BASE_URL + "/ins/{id}/motivations")
	void readItMotivations() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/ins/{id}/motivations", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								IT_MOTIVATIONS_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇 동기부여 정보를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItMotivationsRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("ItMotivationsResponse"))
												.responseFields(Description.success(ItDescription.readItMotivations()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/ins/{id}/motivations")
	void readItMotivations_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/ins/{id}/motivations", -1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								IT_MOTIVATIONS_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇 동기부여 정보를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItMotivationsRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("ItMotivationsResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
