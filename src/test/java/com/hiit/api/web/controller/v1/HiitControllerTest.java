package com.hiit.api.web.controller.v1;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.SupportDescription;
import com.hiit.api.web.dto.request.SuggestItRequest;
import java.time.LocalTime;
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
class HiitControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "Hiit-Controller";
	private static final String BASE_URL = "/api/v1";

	@Test
	@DisplayName(BASE_URL + "/banners")
	void banners() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/banners", 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"Banner",
								resource(
										ResourceSnippetParameters.builder()
												.description("배너 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("BannerRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("BannerResponse"))
												.responseFields(Description.success(SupportDescription.banners()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/notice")
	void notice() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/notice", 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"Notice",
								resource(
										ResourceSnippetParameters.builder()
												.description("알림 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("NoticeRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("NoticeResponse"))
												.responseFields(Description.success(SupportDescription.notice()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/suggest/its")
	void suggestIts() throws Exception {
		// set service mock

		SuggestItRequest request =
				SuggestItRequest.builder()
						.title("제목")
						.start(LocalTime.of(7, 0))
						.end(LocalTime.of(9, 0))
						.reason("이유")
						.build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/suggest/its", 0)
								.header("Authorization", "{{accessToken}}")
								.content(content)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"SuggestIts",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 제안")
												.tag(TAG)
												.requestSchema(Schema.schema("SuggestItsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("SuggestItsResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/suggest/its")
	void suggestIts_notDividedBy30Minutes() throws Exception {
		// set service mock

		SuggestItRequest request =
				SuggestItRequest.builder()
						.title("제목")
						.start(LocalTime.of(7, 10))
						.end(LocalTime.of(9, 0))
						.reason("이유")
						.build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/suggest/its", 0)
								.header("Authorization", "{{accessToken}}")
								.content(content)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"SuggestIts_notDividedBy30Minutes",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 제안")
												.tag(TAG)
												.requestSchema(Schema.schema("SuggestItsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("SuggestItsResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/suggest/its")
	void suggestIts_nullTitle() throws Exception {
		// set service mock

		SuggestItRequest request =
				SuggestItRequest.builder()
						.start(LocalTime.of(7, 0))
						.end(LocalTime.of(9, 0))
						.reason("이유")
						.build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/suggest/its", 0)
								.header("Authorization", "{{accessToken}}")
								.content(content)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"SuggestIts_nullTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 제안")
												.tag(TAG)
												.requestSchema(Schema.schema("SuggestItsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("SuggestItsResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/suggest/its")
	void suggestIts_emptyTitle() throws Exception {
		// set service mock

		SuggestItRequest request =
				SuggestItRequest.builder()
						.title("")
						.start(LocalTime.of(7, 0))
						.end(LocalTime.of(9, 0))
						.reason("이유")
						.build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/suggest/its", 0)
								.header("Authorization", "{{accessToken}}")
								.content(content)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"SuggestIts_emptyTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 제안")
												.tag(TAG)
												.requestSchema(Schema.schema("SuggestItsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("SuggestItsResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/suggest/its")
	void suggestIts_nullReason() throws Exception {
		// set service mock

		SuggestItRequest request =
				SuggestItRequest.builder()
						.title("제목")
						.start(LocalTime.of(7, 0))
						.end(LocalTime.of(9, 0))
						.build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/suggest/its", 0)
								.header("Authorization", "{{accessToken}}")
								.content(content)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"SuggestIts_emptyTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 제안")
												.tag(TAG)
												.requestSchema(Schema.schema("SuggestItsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("SuggestItsResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/suggest/its")
	void suggestIts_emptyReason() throws Exception {
		// set service mock

		SuggestItRequest request =
				SuggestItRequest.builder()
						.title("제목")
						.start(LocalTime.of(7, 0))
						.end(LocalTime.of(9, 0))
						.reason("")
						.build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/suggest/its", 0)
								.header("Authorization", "{{accessToken}}")
								.content(content)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"SuggestIts_emptyTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 제안")
												.tag(TAG)
												.requestSchema(Schema.schema("SuggestItsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("SuggestItsResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/suggest/its")
	void suggestIts_overMaxLengthReason() throws Exception {
		// set service mock
		String overMaxLengthReason =
				"12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		SuggestItRequest request =
				SuggestItRequest.builder()
						.title("")
						.start(LocalTime.of(7, 0))
						.end(LocalTime.of(9, 0))
						.reason(overMaxLengthReason)
						.build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						post(BASE_URL + "/suggest/its", 0)
								.header("Authorization", "{{accessToken}}")
								.content(content)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								"SuggestIts_overMaxLengthReason",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 제안")
												.tag(TAG)
												.requestSchema(Schema.schema("SuggestItsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("SuggestItsResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/errors")
	void errors() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/errors", 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"Errors",
								resource(
										ResourceSnippetParameters.builder()
												.description("에러 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("ErrorsRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("ErrorsResponse"))
												.responseFields(Description.success(SupportDescription.errors()))
												.build())));
	}

	@Test
	@DisplayName(BASE_URL + "/dayCode")
	void dayCode() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/dayCode", 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"DayCode",
								resource(
										ResourceSnippetParameters.builder()
												.description("날짜 코드")
												.tag(TAG)
												.requestSchema(Schema.schema("DayCodeRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("DayCodeResponse"))
												.responseFields(Description.success(SupportDescription.dayCode()))
												.build())));
	}
}
