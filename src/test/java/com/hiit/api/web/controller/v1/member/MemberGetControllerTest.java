package com.hiit.api.web.controller.v1.member;

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
import com.hiit.api.web.controller.description.MemberDescription;
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
class MemberGetControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "Member-Controller";
	private static final String BASE_URL = "/api/v1/members";

	@Test
	@DisplayName(BASE_URL)
	void browseMember() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL, 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"MemberInfo",
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 정보를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberInfoRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("MemberInfoResponse"))
												.responseFields(Description.success(MemberDescription.browseMember()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/stats/it")
	void browseItStat() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/stats/it", 2)
								.header("Authorization", "{{accessToken}}")
								.queryParam("id", "1")
								.queryParam("iid", "1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"MemberItInfo",
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 잇 통계를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberItInfoRequest"))
												.requestHeaders(Description.authHeader())
												.requestParameters(
														parameterWithName("id").description("멤버 id"),
														parameterWithName("iid").description("잇 id"))
												.responseSchema(Schema.schema("MemberItInfoResponse"))
												.responseFields(Description.success(MemberDescription.browseItStat()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/stats/it")
	void browseItStat_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/stats/it", 2)
								.header("Authorization", "{{accessToken}}")
								.queryParam("id", "-1")
								.queryParam("iid", "1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"MemberItInfo_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 잇 통계를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberItInfoRequest"))
												.requestHeaders(Description.authHeader())
												.requestParameters(
														parameterWithName("id").description("멤버 id"),
														parameterWithName("iid").description("잇 id"))
												.responseSchema(Schema.schema("MemberItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/stats/it")
	void browseItStat_invalidIId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/stats/it", 2)
								.header("Authorization", "{{accessToken}}")
								.queryParam("id", "1")
								.queryParam("iid", "-1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"MemberItInfo_invalidIId",
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 잇 통계를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberItInfoRequest"))
												.requestHeaders(Description.authHeader())
												.requestParameters(
														parameterWithName("id").description("멤버 id"),
														parameterWithName("iid").description("잇 id"))
												.responseSchema(Schema.schema("MemberItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
