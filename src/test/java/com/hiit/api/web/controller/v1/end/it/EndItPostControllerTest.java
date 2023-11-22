package com.hiit.api.web.controller.v1.end.it;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.dto.request.end.it.EditEndIpRequest;
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
class EndItPostControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "EndIt-Controller";
	private static final String BASE_URL = "/api/v1/end/its";

	@Test
	@DisplayName(BASE_URL)
	void editEndIt() throws Exception {
		// set service mock

		EditEndIpRequest request = EditEndIpRequest.builder().id(1L).title("종료잇 수정 제목").build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						put(BASE_URL, 0)
								.content(content)
								.header("Authorization", "{{accessToken}}")
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"EditEndItInfo",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditEndItInfoRequest"))
												.requestHeaders(Description.authHeader())
												.responseSchema(Schema.schema("EditEndItInfoResponse"))
												.responseFields(Description.success())
												.build())));
	}
}
