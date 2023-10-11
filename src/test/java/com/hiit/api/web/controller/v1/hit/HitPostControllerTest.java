package com.hiit.api.web.controller.v1.hit;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.ItDescription;
import com.hiit.api.web.dto.request.it.HitRequest;
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
class HitPostControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "Hit";
	private static final String BASE_URL = "/api/v1/hit";

	@Test
	@DisplayName(BASE_URL)
	void inIt() throws Exception {

		HitRequest request = HitRequest.builder().iid(1L).tid(1L).build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL, 0)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"Hit",
								resource(
										ResourceSnippetParameters.builder()
												.description("힛")
												.tag(TAG)
												.requestSchema(Schema.schema("HitRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("HitResponse"))
												.responseFields(Description.success(ItDescription.withOutData()))
												.build())));
	}
}
