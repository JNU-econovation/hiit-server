package com.hiit.api.web.controller.v1.it;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.dto.request.it.AddInItRequest;
import com.hiit.api.web.dto.request.it.DeleteInItRequest;
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
class ItCommandControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	private static final String TAG = "It-Controller";
	private static final String BASE_URL = "/api/v1/its";

	private static final String ADD_INIT_BASE_ID = "AddInIt";

	@Test
	@DisplayName("[POST] " + BASE_URL + "/ins")
	void addInIt() throws Exception {

		String dayCode = String.format("%7s", Long.toBinaryString(0000001L)).replace(' ', '0');
		AddInItRequest request =
				AddInItRequest.builder().id(1L).dayCode(dayCode).resolution("다짐").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								ADD_INIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddInItRequest"))
												.responseSchema(Schema.schema("AddInItResponse"))
												.responseFields(Description.created())
												.build())));
	}

	@Test
	@DisplayName("[POST/invalidId] " + BASE_URL + "/ins")
	void addInIt_invalidId() throws Exception {

		String dayCode = String.format("%7s", Long.toBinaryString(0000001L)).replace(' ', '0');
		AddInItRequest request =
				AddInItRequest.builder().id(-1L).dayCode(dayCode).resolution("다짐").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_INIT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddInItRequest"))
												.responseSchema(Schema.schema("AddInItResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[POST/invalidDaycode] " + BASE_URL + "/ins")
	void addInIt_invalidDayCode() throws Exception {

		String dayCode = Long.toBinaryString(0000001L);
		AddInItRequest request =
				AddInItRequest.builder().id(1L).dayCode(dayCode).resolution("다짐").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_INIT_BASE_ID + "_invalidDayCode",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddInItRequest"))
												.responseSchema(Schema.schema("AddInItResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[POST/nullResolution] " + BASE_URL + "/ins")
	void addInIt_nullResolution() throws Exception {

		String dayCode = String.format("%7s", Long.toBinaryString(0000001L)).replace(' ', '0');
		AddInItRequest request = AddInItRequest.builder().id(1L).dayCode(dayCode).build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_INIT_BASE_ID + "_nullResolution",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddInItRequest"))
												.responseSchema(Schema.schema("AddInItResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[POST/emptyResolution] " + BASE_URL + "/ins")
	void addInIt_emptyResolution() throws Exception {

		String dayCode = String.format("%7s", Long.toBinaryString(0000001L)).replace(' ', '0');
		AddInItRequest request =
				AddInItRequest.builder().id(1L).dayCode(dayCode).resolution("").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_INIT_BASE_ID + "_emptyResolution",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddInItRequest"))
												.responseSchema(Schema.schema("AddInItResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[POST/overMaxLength] " + BASE_URL + "/ins")
	void addInIt_overMaxLength() throws Exception {

		String dayCode = String.format("%7s", Long.toBinaryString(0000001L)).replace(' ', '0');
		String overMaxLength = "1234567890123456"; // max : 15
		AddInItRequest request =
				AddInItRequest.builder().id(1L).dayCode(dayCode).resolution(overMaxLength).build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						post(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								ADD_INIT_BASE_ID + "_overMaxLength",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇에 참여한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("AddInItRequest"))
												.responseSchema(Schema.schema("AddInItResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	private static final String EDIT_INIT_BASE_ID = "EditInIt";

	@Test
	@DisplayName("[PUT] " + BASE_URL + "/ins")
	void editInIt() throws Exception {

		String dayCode = String.format("%7s", Long.toBinaryString(0000001L)).replace(' ', '0');
		AddInItRequest request =
				AddInItRequest.builder().id(1L).dayCode(dayCode).resolution("다짐").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(put(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								EDIT_INIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여 정보를 수정한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("EditInItRequest"))
												.responseSchema(Schema.schema("EditInItResponse"))
												.responseFields(Description.success())
												.build())));
	}

	private static final String DELETE_INIT_BASE_ID = "DeleteInIt";

	@Test
	@DisplayName("[DELETE] " + BASE_URL + "/ins")
	void deleteInIt() throws Exception {

		DeleteInItRequest request = DeleteInItRequest.builder().id(1L).endTitle("종료 잇 제목").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						delete(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								DELETE_INIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여를 종료한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteInItRequest"))
												.responseSchema(Schema.schema("DeleteInItResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName("[DELETE/invalidId] " + BASE_URL + "/ins")
	void deleteInIt_invalidId() throws Exception {

		DeleteInItRequest request = DeleteInItRequest.builder().id(-1L).endTitle("종료 잇 제목").build();

		String content = objectMapper.writeValueAsString(request);

		// set service mock

		mockMvc
				.perform(
						delete(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								DELETE_INIT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여를 종료한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteInItRequest"))
												.responseSchema(Schema.schema("DeleteInItResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName("[DELETE/nullTitle] " + BASE_URL)
	void deleteInIt_nullTitle() throws Exception {
		// set service mock
		DeleteInItRequest request = DeleteInItRequest.builder().id(1L).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						delete(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								DELETE_INIT_BASE_ID + "_nullTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여를 종료한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteInItRequest"))
												.responseSchema(Schema.schema("DeleteInItResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName("[DELETE/emptyTitle] " + BASE_URL)
	void deleteInIt_emptyTitle() throws Exception {
		// set service mock
		DeleteInItRequest request = DeleteInItRequest.builder().id(1L).endTitle("").build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						delete(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								DELETE_INIT_BASE_ID + "_emptyTitle",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여를 종료한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteInItRequest"))
												.responseSchema(Schema.schema("DeleteInItResponse"))
												.responseFields(Description.success())
												.build())));
	}

	@Test
	@DisplayName("[DELETE/overMaxLength] " + BASE_URL)
	void deleteInIt_overMaxLength() throws Exception {
		// set service mock
		String overMaxLength = "1234567890123456"; // max : 15
		DeleteInItRequest request = DeleteInItRequest.builder().id(1L).endTitle(overMaxLength).build();

		String content = objectMapper.writeValueAsString(request);

		mockMvc
				.perform(
						delete(BASE_URL + "/ins", 0).content(content).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								DELETE_INIT_BASE_ID + "_overMaxLength",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 참여를 종료한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("DeleteInItRequest"))
												.responseSchema(Schema.schema("DeleteInItResponse"))
												.responseFields(Description.success())
												.build())));
	}
}
