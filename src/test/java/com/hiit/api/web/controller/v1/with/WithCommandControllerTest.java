package com.hiit.api.web.controller.v1.with;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.dto.request.with.AddWithRequest;
import com.hiit.api.web.dto.request.with.DeleteWithRequest;
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
class WithCommandControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "With-Controller";
	private static final String BASE_URL = "/api/v1/its/withs";

	private static final String ADD_WITH_BASE_ID = "AddWithInfo";

	@Test
	@DisplayName(BASE_URL)
	void readWiths() throws Exception {
		// set service mock
		AddWithRequest request = AddWithRequest.builder().id(1L).content("윗 내용").build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(post(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								ADD_WITH_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 추가한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddWithRequest"))
												.responseSchema(Schema.schema("AddWithResponse"))
												.responseFields(Description.created())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void readWiths_invalidId() throws Exception {
		// set service mock
		AddWithRequest request = AddWithRequest.builder().id(-1L).content("윗 내용").build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(post(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_WITH_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 추가한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddWithRequest"))
												.responseSchema(Schema.schema("AddWithResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void readWiths_nullWith() throws Exception {
		// set service mock
		AddWithRequest request = AddWithRequest.builder().id(1L).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(post(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_WITH_BASE_ID + "_nullWith",
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 추가한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddWithRequest"))
												.responseSchema(Schema.schema("AddWithResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void readWiths_emptyWith() throws Exception {
		// set service mock
		AddWithRequest request = AddWithRequest.builder().id(1L).content("").build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(post(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_WITH_BASE_ID + "_emptyWith",
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 추가한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddWithRequest"))
												.responseSchema(Schema.schema("AddWithResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void readWiths_overMaxLength() throws Exception {
		// set service mock
		String overMaxLength = "123456789012345678901";
		AddWithRequest request = AddWithRequest.builder().id(1L).content(overMaxLength).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(post(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_WITH_BASE_ID + "_overMaxLength",
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 추가한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddWithRequest"))
												.responseSchema(Schema.schema("AddWithResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	private static final String DELETE_WITH_BASE_ID = "DeleteWithInfo";

	@Test
	@DisplayName(BASE_URL)
	void deleteWith() throws Exception {
		// set service mock
		DeleteWithRequest request = DeleteWithRequest.builder().id(1L).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(delete(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								DELETE_WITH_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 삭제한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteWithRequest"))
												.responseSchema(Schema.schema("DeleteWithResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName(BASE_URL)
	void deleteWith_invalidId() throws Exception {
		// set service mock
		DeleteWithRequest request = DeleteWithRequest.builder().id(-1L).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(delete(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								DELETE_WITH_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("윗 삭제한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteWithRequest"))
												.responseSchema(Schema.schema("DeleteWithResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
