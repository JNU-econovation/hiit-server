package com.hiit.api.web.controller.v1.member;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.dto.response.member.UserAuthToken;
import com.hiit.api.domain.usecase.member.ConsentNotificationUseCase;
import com.hiit.api.domain.usecase.member.DeleteMemberUseCase;
import com.hiit.api.domain.usecase.member.DissentNotificationUseCase;
import com.hiit.api.domain.usecase.member.FacadeCreateMemberUseCase;
import com.hiit.api.domain.usecase.member.GetTokenUseCase;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.MemberDescription;
import com.hiit.api.web.dto.request.member.CreateSocialMemberRequest;
import com.hiit.api.web.dto.request.member.NotificationConsentRequest;
import com.hiit.api.web.dto.request.member.SocialSubject;
import com.hiit.api.web.dto.request.member.TokenRefreshRequest;
import com.hiit.config.security.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {AppMain.class, TestSecurityConfig.class})
class MemberCommandControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private FacadeCreateMemberUseCase facadeCreateMemberUseCase;
	@MockBean private GetTokenUseCase getTokenUseCase;
	@MockBean private ConsentNotificationUseCase consentNotificationUseCase;
	@MockBean private DissentNotificationUseCase dissentNotificationUseCase;
	@MockBean private DeleteMemberUseCase deleteMemberUseCase;

	private static final String TAG = "Member-Controller";
	private static final String BASE_URL = "/api/v1/members";

	@Test
	@DisplayName("[POST] " + BASE_URL)
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void save() throws Exception {
		// set service mock
		CreateSocialMemberRequest request =
				CreateSocialMemberRequest.builder().code("code").socialSubject(SocialSubject.KAKAO).build();
		String content = objectMapper.writeValueAsString(request);
		content = content.replace(SocialSubject.KAKAO.name(), "kakao");

		when(facadeCreateMemberUseCase.execute(any())).thenReturn(getUserAuthTokenMockResponse());
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

	private UserAuthToken getUserAuthTokenMockResponse() {
		return UserAuthToken.builder().accessToken("accessToken").refreshToken("refreshToken").build();
	}

	@Test
	@DisplayName("[DELETE] " + BASE_URL)
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void delete() throws Exception {
		// set service mock
		when(deleteMemberUseCase.execute(any())).thenReturn(AbstractResponse.VOID);

		mockMvc
				.perform(
						RestDocumentationRequestBuilders.delete(BASE_URL, 0)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"DeleteMember",
								resource(
										ResourceSnippetParameters.builder()
												.description("회원탈퇴")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteMemberRequest"))
												.responseSchema(Schema.schema("DeleteMemberResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName("[POST] " + BASE_URL + "/token")
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void token() throws Exception {
		// set service mock
		TokenRefreshRequest request =
				TokenRefreshRequest.builder().token("dkjafd.dfkajdf.dfjk").build();
		String content = objectMapper.writeValueAsString(request);

		when(getTokenUseCase.execute(any())).thenReturn(getUserAuthTokenMockResponse());

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

	@Test
	@DisplayName("[POST] " + BASE_URL + "/notification")
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void notificationConsent() throws Exception {
		// set service mock
		NotificationConsentRequest request =
				NotificationConsentRequest.builder().device("device").build();

		String content = objectMapper.writeValueAsString(request);

		when(consentNotificationUseCase.execute(any())).thenReturn(AbstractResponse.VOID);

		mockMvc
				.perform(
						post(BASE_URL + "/notification", 0)
								.contentType(MediaType.APPLICATION_JSON)
								.content(content))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"NotificationConsent",
								resource(
										ResourceSnippetParameters.builder()
												.description("알림 동의")
												.tag(TAG)
												.requestSchema(Schema.schema("NotificationConsentRequest"))
												.responseSchema(Schema.schema("NotificationConsentResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName("[DELETE] " + BASE_URL + "/notification")
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void notificationDissent() throws Exception {
		// set service mock

		when(dissentNotificationUseCase.execute(any())).thenReturn(AbstractResponse.VOID);

		mockMvc
				.perform(
						RestDocumentationRequestBuilders.delete(BASE_URL + "/notification", 0)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"NotificationDissent",
								resource(
										ResourceSnippetParameters.builder()
												.description("알림 동의 철회")
												.tag(TAG)
												.requestSchema(Schema.schema("NotificationDissentRequest"))
												.responseSchema(Schema.schema("NotificationDissentResponse"))
												.responseFields(Description.success())
												.build())));
	}
}
