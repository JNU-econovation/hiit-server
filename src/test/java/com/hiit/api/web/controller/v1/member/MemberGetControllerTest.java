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
import java.util.List;
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
	private static final String TAG = "Member";
	private static final String BASE_URL = "/api/v1/members";

	@Test
	@DisplayName(BASE_URL + "/friends")
	void browseFriend() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/friends", 0)
								.header("Authorization", "{{accessToken}}")
								.param("ban", "false")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"BrowseFriend",
								resource(
										ResourceSnippetParameters.builder()
												.description("친구 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseFriendRequest"))
												.requestParameters(
														parameterWithName("ban")
																.description("차단 친구 조회 여부")
																.optional()
																.defaultValue("false"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseFriendResponse"))
												.responseFields(Description.success(MemberDescription.browseFriend()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/mypage")
	void mypage() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/mypage", 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"MyPage",
								resource(
										ResourceSnippetParameters.builder()
												.description("나의 프로필")
												.tag(TAG)
												.requestSchema(Schema.schema("MyPageRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("MyPageResponse"))
												.responseFields(Description.success(MemberDescription.mypage()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{mid}")
	void read() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{mid}", 1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"ReadMember",
								resource(
										ResourceSnippetParameters.builder()
												.description("멤버의 프로필")
												.tag(TAG)
												.requestSchema(Schema.schema("ReadMemberRequest"))
												.pathParameters(parameterWithName("mid").description("member id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("ReadMemberResponse"))
												.responseFields(Description.success(MemberDescription.read()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{mid}/common/its")
	void browseCommonIts() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{mid}/common/its", 1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"BrowseCommonIts",
								resource(
										ResourceSnippetParameters.builder()
												.description("멤버와 공통 참여 잇 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BrowseCommonItsRequest"))
												.pathParameters(parameterWithName("mid").description("member id"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BrowseCommonItsResponse"))
												.responseFields(Description.success(MemberDescription.browseCommonIts()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/{mid}/common/its/{iid}/together")
	void readMemberItCommonTogether() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/{mid}/common/its/{iid}/together", 1, 1)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"ReadMemberItCommonTogether",
								resource(
										ResourceSnippetParameters.builder()
												.description("멤버의 잇 함께하기 정보")
												.tag(TAG)
												.requestSchema(Schema.schema("ReadMemberItCommonTogetherRequest"))
												.pathParameters(
														List.of(
																parameterWithName("mid").description("member id"),
																parameterWithName("iid").description("it id")))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("ReadMemberItCommonTogetherResponse"))
												.responseFields(
														Description.success(MemberDescription.readMemberItCommonTogether()))
												.build())));
	}
}
