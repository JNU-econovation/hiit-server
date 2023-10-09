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
class ItGetControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "It";
	private static final String BASE_URL = "/api/v1/its";

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
								"BrowseIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 주제 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseItRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseItResponse"))
												.responseFields(Description.success(ItDescription.browseIt()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/in")
	void browseInIt() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/in", 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"BrowseInIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("멤버가 참여중인 잇 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseInItRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseInItResponse"))
												.responseFields(Description.success(ItDescription.browseInIt()))
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
								"ReadIt",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇이 주제 정보")
												.tag(TAG)
												.requestSchema(Schema.schema("ReadItRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("ReadItResponse"))
												.responseFields(Description.success(ItDescription.readIt()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{id}/together")
	void browseTogether() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{id}/together", 1)
								.param("all", "false")
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"BrowseTogether",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇이 주제 정보")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseTogetherRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestParameters(
														parameterWithName("all")
																.description("전체 조회 여부")
																.optional()
																.defaultValue("false"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseTogetherResponse"))
												.responseFields(Description.success(ItDescription.browseTogether()))
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
								"BrowseMyTogether",
								resource(
										ResourceSnippetParameters.builder()
												.description("나의 잇 함께하기 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseMyTogetherRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseMyTogetherResponse"))
												.responseFields(Description.success(ItDescription.browseMyTogether()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{id}/together/rank")
	void browseRankTogether() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{id}/together/rank", 1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"BrowseRankTogether",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 함께하기 랭킹 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseRankTogetherRequest"))
												.pathParameters(parameterWithName("id").description("it id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseRankTogetherResponse"))
												.responseFields(Description.success(ItDescription.browseRankTogether()))
												.build())));
	}
}
