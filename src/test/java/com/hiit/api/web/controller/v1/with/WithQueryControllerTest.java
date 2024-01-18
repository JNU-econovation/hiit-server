package com.hiit.api.web.controller.v1.with;

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
import com.hiit.api.domain.dto.response.with.WithInfo;
import com.hiit.api.domain.dto.response.with.WithMemberInfo;
import com.hiit.api.domain.dto.response.with.WithPage;
import com.hiit.api.domain.usecase.with.GetWithsUseCase;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.WithDescription;
import com.hiit.config.security.TestSecurityConfig;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {AppMain.class, TestSecurityConfig.class})
class WithQueryControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private GetWithsUseCase getWithsUseCase;

	private static final String TAG = "With-Controller";
	private static final String BASE_URL = "/api/v1/its/withs";

	private static final String WITH_BASE_ID = "WithInfo";

	@Test
	@DisplayName("[GET] " + BASE_URL)
	@WithUserDetails(userDetailsServiceBeanName = "testTokenUserDetailsService")
	void readWiths() throws Exception {
		// set service mock
		when(getWithsUseCase.execute(any())).thenReturn(getWithPageMockResponse());

		mockMvc
				.perform(
						get(BASE_URL, 0)
								.queryParam("page", "0")
								.queryParam("size", "10")
								.queryParam("sort", "id,desc")
								.queryParam("id", "1")
								.queryParam("my", "true")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								WITH_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 정보 조회를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("WithInfoRequest"))
												.requestParameters(
														parameterWithName("id").description("잇 id"),
														parameterWithName("my").description("나의 윗 정보 조회 여부"),
														parameterWithName("page").description("페이지 번호"),
														parameterWithName("size").description("페이지 크기"),
														parameterWithName("sort").description("정렬 조건"))
												.responseSchema(Schema.schema("WithInfoResponse"))
												.responseFields(Description.success(WithDescription.readWiths()))
												.build())));
	}

	private WithPage getWithPageMockResponse() {
		WithMemberInfo withMemberInfo =
				WithMemberInfo.builder().name("멤버 이름").profile("멤버 프로필").resolution("멤버 다짐").build();
		WithInfo withInfo1 =
				WithInfo.builder().id(1L).content("윗 내용").hit(10L).withMemberInfo(withMemberInfo).build();
		WithInfo withInfo2 =
				WithInfo.builder().id(2L).content("윗 내용").hit(10L).withMemberInfo(withMemberInfo).build();
		List<WithInfo> withInfos = List.of(withInfo1, withInfo2);
		PageImpl<WithInfo> withInfoPage =
				new PageImpl<>(withInfos, PageRequest.of(1, 10), withInfos.size());
		return new WithPage(withInfoPage);
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL)
	void readWiths_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL, 0)
								.queryParam("page", "0")
								.queryParam("size", "10")
								.queryParam("sort", "id,desc")
								.queryParam("id", "-1")
								.queryParam("my", "true")
								.queryParam("random", "true")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								WITH_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 정보 조회를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("WithInfoRequest"))
												.requestParameters(
														parameterWithName("id").description("윗 id"),
														parameterWithName("my").description("나의 윗 정보 조회 여부"),
														parameterWithName("page").description("페이지 번호"),
														parameterWithName("size").description("페이지 크기"),
														parameterWithName("sort").description("정렬 조건"),
														parameterWithName("random").description("랜덤 조회 여부"))
												.responseSchema(Schema.schema("WithInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
