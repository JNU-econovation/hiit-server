package com.hiit.api.web.controller.v1.support;

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
class SupportControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "Support";
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
	@DisplayName(BASE_URL + "/noti")
	void noti() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/noti", 0)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"Noti",
								resource(
										ResourceSnippetParameters.builder()
												.description("알림 내역")
												.tag(TAG)
												.requestSchema(Schema.schema("NotiRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("NotiResponse"))
												.responseFields(Description.success(SupportDescription.noti()))
												.build())));
	}
}
