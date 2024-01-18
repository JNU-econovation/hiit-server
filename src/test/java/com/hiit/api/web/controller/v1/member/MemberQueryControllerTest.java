package com.hiit.api.web.controller.v1.member;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.domain.dto.response.member.MemberInfo;
import com.hiit.api.domain.dto.response.member.MemberItInfo;
import com.hiit.api.domain.usecase.member.GetMemberInfoUseCase;
import com.hiit.api.domain.usecase.member.GetMemberItInfoUseCase;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.MemberDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = AppMain.class)
class MemberQueryControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private GetMemberInfoUseCase getMemberInfoUseCase;
	@MockBean private GetMemberItInfoUseCase getMemberItInfoUseCase;

	private static final String TAG = "Member-Controller";
	private static final String BASE_URL = "/api/v1/members";

	private static final String MEMBER_BASE_ID = "MemberInfo";

	@Test
	@DisplayName("[GET] " + BASE_URL)
	void browseMember() throws Exception {
		// set service mock
		when(getMemberInfoUseCase.execute(any())).thenReturn(getMemberInfoMockResponse());

		mockMvc
				.perform(get(BASE_URL + "/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								MEMBER_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 정보를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberInfoRequest"))
												.pathParameters(parameterWithName("id").description("멤버 id"))
												.responseSchema(Schema.schema("MemberInfoResponse"))
												.responseFields(Description.success(MemberDescription.browseMember()))
												.build())));
	}

	private MemberInfo getMemberInfoMockResponse() {
		return MemberInfo.builder().id(1L).name("멤버 이름").profile("멤버 프로필").inItCount(10L).build();
	}

	private static final String MEMBER_IT_BASE_ID = "MemberItInfo";

	@Test
	@DisplayName("[GET] " + BASE_URL + "/stats/it")
	void browseItStat() throws Exception {
		// set service mock
		when(getMemberItInfoUseCase.execute(any())).thenReturn(getMemberItInfoMockResponse());

		mockMvc
				.perform(
						get(BASE_URL + "/stats/it", 2)
								.queryParam("id", "1")
								.queryParam("iid", "1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								MEMBER_IT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 잇 통계를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberItInfoRequest"))
												.requestParameters(
														parameterWithName("id").description("멤버 id"),
														parameterWithName("iid").description("잇 id"))
												.responseSchema(Schema.schema("MemberItInfoResponse"))
												.responseFields(Description.success(MemberDescription.browseItStat()))
												.build())));
	}

	private MemberItInfo getMemberItInfoMockResponse() {
		return MemberItInfo.builder()
				.id(1L)
				.name("멤버 이름")
				.profile("멤버 프로필")
				.itInfo("00에 0번 참여중입니다.")
				.build();
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL + "/stats/it")
	void browseItStat_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/stats/it", 2)
								.queryParam("id", "-1")
								.queryParam("iid", "1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								MEMBER_IT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 잇 통계를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberItInfoRequest"))
												.requestParameters(
														parameterWithName("id").description("멤버 id"),
														parameterWithName("iid").description("잇 id"))
												.responseSchema(Schema.schema("MemberItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL + "/stats/it")
	void browseItStat_invalidIId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/stats/it", 2)
								.queryParam("id", "1")
								.queryParam("iid", "-1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								MEMBER_IT_BASE_ID + "_invalidIId",
								resource(
										ResourceSnippetParameters.builder()
												.description("회원 잇 통계를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("MemberItInfoRequest"))
												.requestParameters(
														parameterWithName("id").description("멤버 id"),
														parameterWithName("iid").description("잇 id"))
												.responseSchema(Schema.schema("MemberItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
