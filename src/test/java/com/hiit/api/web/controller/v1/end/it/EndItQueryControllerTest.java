package com.hiit.api.web.controller.v1.end.it;

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
import com.hiit.api.domain.dto.response.end.it.EndItInfo;
import com.hiit.api.domain.dto.response.end.it.EndItInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithInfo;
import com.hiit.api.domain.dto.response.end.with.EndWithInfos;
import com.hiit.api.domain.dto.response.end.with.EndWithMemberInfo;
import com.hiit.api.domain.usecase.end.it.GetEndItUseCase;
import com.hiit.api.domain.usecase.end.it.GetEndItsUseCase;
import com.hiit.api.domain.usecase.end.it.GetEndWithsUseCase;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.EndItDescription;
import com.hiit.config.security.TestSecurityConfig;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {AppMain.class, TestSecurityConfig.class})
class EndItQueryControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private GetEndItUseCase getEndItUseCase;
	@MockBean private GetEndItsUseCase getEndItsUseCase;
	@MockBean private GetEndWithsUseCase getEndWithsUseCase;

	private static final String TAG = "EndIt-Controller";
	private static final String BASE_URL = "/api/v1/end/its";

	private static final String ENDIT_BASE_ID = "EndItInfo";

	@Test
	@DisplayName("[GET] " + BASE_URL + "/{id}")
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void browseEndIt() throws Exception {
		// set service mock
		when(getEndItUseCase.execute(any())).thenReturn(getEndItMockResponse());

		mockMvc
				.perform(get(BASE_URL + "/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								ENDIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇")
												.tag(TAG)
												.requestSchema(Schema.schema("EndItInfoRequest"))
												.pathParameters(parameterWithName("id").description("종료 잇 id"))
												.responseSchema(Schema.schema("EndItInfoResponse"))
												.responseFields(Description.success(EndItDescription.browseEndIt()))
												.build())));
	}

	private EndItInfo getEndItMockResponse() {
		return EndItInfo.builder()
				.id(1L)
				.title("종료 잇 제목")
				.topic("종료 잇 주제")
				.startTime(LocalTime.of(7, 0))
				.endTime(LocalTime.of(9, 0))
				.startDate(LocalDate.now())
				.endDate(LocalDate.now())
				.withCount(10L)
				.build();
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL + "/{id}")
	void browseEndIt_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/{id}", -1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ENDIT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇")
												.tag(TAG)
												.requestSchema(Schema.schema("EndItInfoRequest"))
												.pathParameters(parameterWithName("id").description("종료 잇 id"))
												.responseSchema(Schema.schema("EndItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[GET] " + BASE_URL)
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void readEndIts() throws Exception {
		// set service mock
		when(getEndItsUseCase.execute(any())).thenReturn(getEndItInfosMockResponse());

		mockMvc
				.perform(get(BASE_URL, 0).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								ENDIT_BASE_ID + "s",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 목록")
												.tag(TAG)
												.requestSchema(Schema.schema("EndItInfosRequest"))
												.responseFields(Description.success(EndItDescription.readEndIts()))
												.build())));
	}

	private EndItInfos getEndItInfosMockResponse() {
		EndItInfo endIt1 =
				EndItInfo.builder()
						.id(1L)
						.title("종료 잇 제목")
						.topic("종료 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.startDate(LocalDate.now())
						.endDate(LocalDate.now())
						.withCount(10L)
						.build();
		EndItInfo endIt2 =
				EndItInfo.builder()
						.id(2L)
						.title("종료 잇 제목")
						.topic("종료 잇 주제")
						.startTime(LocalTime.of(10, 0))
						.endTime(LocalTime.of(12, 0))
						.startDate(LocalDate.now())
						.endDate(LocalDate.now())
						.withCount(10L)
						.build();

		return new EndItInfos(List.of(endIt1, endIt2));
	}

	private static final String END_WITHIN_BASE_ID = "EndWithinInfo";

	@Test
	@DisplayName("[GET] " + BASE_URL + "/withs")
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void browseEndWith() throws Exception {
		// set service mock
		when(getEndWithsUseCase.execute(any())).thenReturn(getEndWithInfosMockResponse());

		mockMvc
				.perform(
						get(BASE_URL + "/withs", 0)
								.queryParam("id", "1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								END_WITHIN_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 윗")
												.tag(TAG)
												.requestSchema(Schema.schema("EndWithInfoRequest"))
												.requestParameters(parameterWithName("id").description("종료 잇 id"))
												.responseSchema(Schema.schema("EndWithInfoResponse"))
												.responseFields(Description.success(EndItDescription.browseEndWith()))
												.build())));
	}

	private EndWithInfos getEndWithInfosMockResponse() {
		EndWithMemberInfo withMemberInfo =
				EndWithMemberInfo.builder().id(1L).profile("프로필 사진").name("이름").resolution("잇 다짐").build();
		EndWithInfo withInfo1 =
				EndWithInfo.builder()
						.id(1L)
						.content("윗 내용")
						.hit(10L)
						.withMemberInfo(withMemberInfo)
						.build();
		EndWithInfo withInfo2 =
				EndWithInfo.builder()
						.id(2L)
						.content("윗 내용")
						.hit(10L)
						.withMemberInfo(withMemberInfo)
						.build();
		return new EndWithInfos(List.of(withInfo1, withInfo2));
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL + "/withs")
	void browseEndWith_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/withs", 0)
								.queryParam("id", "-1")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								END_WITHIN_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 윗")
												.tag(TAG)
												.requestSchema(Schema.schema("EndWithInfoRequest"))
												.requestParameters(parameterWithName("id").description("종료 잇 id"))
												.responseSchema(Schema.schema("EndWithInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
