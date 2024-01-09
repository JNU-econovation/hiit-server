package com.hiit.api.web.controller.v1.member;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.MemberDescription;
import com.hiit.api.web.dto.request.member.CreateSocialMemberRequest;
import com.hiit.api.web.dto.request.member.SocialSubject;
import com.hiit.api.web.dto.request.member.TokenRefreshRequest;
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
class MemberCommandControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "Member-Controller";
	private static final String BASE_URL = "/api/v1/members";

	@Test
	@DisplayName("[POST] " + BASE_URL)
	void save() throws Exception {
		// set service mock
		CreateSocialMemberRequest request =
				CreateSocialMemberRequest.builder().code("1234").socialSubject(SocialSubject.KAKAO).build();
		String content = objectMapper.writeValueAsString(request);
		content = content.replace(SocialSubject.KAKAO.name(), "kakao");

		mockMvc
				.perform(post(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"Login/Save",
								resource(
										ResourceSnippetParameters.builder()
												.description("로그인/회원가입")
												.tag(TAG)
												.requestSchema(Schema.schema("LoginSaveRequest"))
												.responseSchema(Schema.schema("LoginSaveResponse"))
												.responseFields(Description.success(MemberDescription.authToken()))
												.build())));
	}

	@Test
	@DisplayName("[POST] " + BASE_URL + "/token")
	void token() throws Exception {
		// set service mock
		TokenRefreshRequest request =
				TokenRefreshRequest.builder().token("dkjafd.dfkajdf.dfjk").build();
		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/token", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"Token",
								resource(
										ResourceSnippetParameters.builder()
												.description("토큰 갱신")
												.tag(TAG)
												.requestSchema(Schema.schema("TokenRefreshRequest"))
												.responseSchema(Schema.schema("TokenRefreshResponse"))
												.responseFields(Description.success(MemberDescription.authToken()))
												.build())));
	}
}
