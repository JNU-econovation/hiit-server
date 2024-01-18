package com.hiit.api.web.controller.v1.end.it;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.common.marker.dto.AbstractResponse;
import com.hiit.api.domain.usecase.end.it.DeleteEndItUseCase;
import com.hiit.api.domain.usecase.end.it.EditEndItUseCase;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.dto.request.end.it.DeleteEndItRequest;
import com.hiit.api.web.dto.request.end.it.EditEndItRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = AppMain.class)
class EndItCommandControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private DeleteEndItUseCase deleteEndItUseCase;
	@MockBean private EditEndItUseCase editEndItUseCase;
	private static final String TAG = "EndIt-Controller";
	private static final String BASE_URL = "/api/v1/end/its";

	@Test
	@DisplayName("[PUT] " + BASE_URL)
	void editEndIt() throws Exception {
		// set service mock

		EditEndItRequest request = EditEndItRequest.builder().id(1L).title("종료잇 수정 제목").build();

		String content = objectMapper.writeValueAsString(request);

		when(editEndItUseCase.execute(any())).thenReturn(AbstractResponse.VOID);

		mockMvc
				.perform(put(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								"EditEndItInfo",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditEndItInfoRequest"))
												.responseSchema(Schema.schema("EditEndItInfoResponse"))
												.responseFields(Description.success())
												.build())));
	}

	private static final String EDIT_ENDIT_BASE_ID = "EndItInfo";

	@Test
	@DisplayName("[PUT/InvalidId] " + BASE_URL)
	void editEndIt_invalidId() throws Exception {
		// set service mock

		EditEndItRequest request = EditEndItRequest.builder().id(-1L).title("종료잇 수정 제목").build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(put(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								EDIT_ENDIT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditEndItInfoRequest"))
												.responseSchema(Schema.schema("EditEndItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[PUT/nullTitle] " + BASE_URL)
	void editEndIt_nullTitle() throws Exception {
		// set service mock

		EditEndItRequest request = EditEndItRequest.builder().id(1L).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(put(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								EDIT_ENDIT_BASE_ID + "_nullTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditEndItInfoRequest"))
												.responseSchema(Schema.schema("EditEndItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[PUT/emptyTitle] " + BASE_URL)
	void editEndIt_emptyTitle() throws Exception {
		// set service mock

		EditEndItRequest request = EditEndItRequest.builder().id(1L).title("").build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(put(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								EDIT_ENDIT_BASE_ID + "_emptyTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditEndItInfoRequest"))
												.responseSchema(Schema.schema("EditEndItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[PUT/overMaxLength] " + BASE_URL)
	void editEndIt_overMaxLength() throws Exception {
		// set service mock
		String overMaxLength = "1234567890123456"; // max : 15
		EditEndItRequest request = EditEndItRequest.builder().id(1L).title(overMaxLength).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(put(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								EDIT_ENDIT_BASE_ID + "_overMaxLength",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditEndItInfoRequest"))
												.responseSchema(Schema.schema("EditEndItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	private static final String DELETE_ENDIT_BASE_ID = "DeleteEndItInfo";

	@Test
	@DisplayName("[DELETE] " + BASE_URL)
	void deleteEndIt() throws Exception {
		// set service mock

		DeleteEndItRequest request = DeleteEndItRequest.builder().id(1L).build();

		String content = objectMapper.writeValueAsString(request);

		when(deleteEndItUseCase.execute(any())).thenReturn(AbstractResponse.VOID);

		mockMvc
				.perform(delete(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								DELETE_ENDIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 숨긴다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteEndItInfoRequest"))
												.responseSchema(Schema.schema("DeleteEndItInfoResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName("[DELETE/invalidId] " + BASE_URL)
	void deleteEndIt_invalidId() throws Exception {
		// set service mock

		DeleteEndItRequest request = DeleteEndItRequest.builder().id(-1L).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(delete(BASE_URL, 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								DELETE_ENDIT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("종료 잇 숨긴다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteEndItInfoRequest"))
												.responseSchema(Schema.schema("DeleteEndItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
