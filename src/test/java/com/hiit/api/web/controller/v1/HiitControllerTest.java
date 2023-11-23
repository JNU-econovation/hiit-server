package com.hiit.api.web.controller.v1;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.SupportDescription;
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
