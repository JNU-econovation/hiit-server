package com.hiit.api.web.controller.v1.it;

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
import com.hiit.api.domain.dto.response.it.InItInfo;
import com.hiit.api.domain.dto.response.it.InItInfos;
import com.hiit.api.domain.dto.response.it.ItInfo;
import com.hiit.api.domain.dto.response.it.ItInfos;
import com.hiit.api.domain.dto.response.it.ItMotivations;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.domain.usecase.it.GetInItUseCase;
import com.hiit.api.domain.usecase.it.GetInItsUseCase;
import com.hiit.api.domain.usecase.it.GetItUseCase;
import com.hiit.api.domain.usecase.it.GetItsUseCase;
import com.hiit.api.domain.usecase.it.InItMotivationUseCase;
import com.hiit.api.web.controller.description.Description;
import com.hiit.api.web.controller.description.ItDescription;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(value = "test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = AppMain.class)
class ItQueryControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@MockBean private GetItUseCase getItUseCase;
	@MockBean private GetItsUseCase getItsUseCase;
	@MockBean private GetInItUseCase getInItUseCase;
	@MockBean private GetInItsUseCase getInItsUseCase;
	@MockBean private InItMotivationUseCase inItMotivationUseCase;

	private static final String TAG = "It-Controller";
	private static final String BASE_URL = "/api/v1/its";

	private static final String IT_BASE_ID = "ItInfo";

	@Test
	@DisplayName("[GET] " + BASE_URL + "/{id}")
	void browseIt() throws Exception {
		// set service mock
		when(getItUseCase.execute(any())).thenReturn(getItInfoMockResponse());

		mockMvc
				.perform(get(BASE_URL + "/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								IT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItInfoRequest"))
												.pathParameters(parameterWithName("id").description("잇 id"))
												.responseSchema(Schema.schema("ItInfoResponse"))
												.responseFields(Description.success(ItDescription.browseIt()))
												.build())));
	}

	private ItInfo getItInfoMockResponse() {
		return ItInfo.builder()
				.id(1L)
				.topic("잇 주제")
				.startTime(LocalTime.of(1, 0))
				.endTime(LocalTime.of(2, 0))
				.inMemberCount(10L)
				.memberIn(true)
				.type(ItTypeDetails.IT_REGISTERED.getValue())
				.build();
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL + "/{id}")
	void browseIt_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/{id}", -1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								IT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItInfoRequest"))
												.pathParameters(parameterWithName("id").description("잇 id"))
												.responseSchema(Schema.schema("ItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	@Test
	@DisplayName("[GET] " + BASE_URL)
	void readIts() throws Exception {
		// set service mock
		when(getItsUseCase.execute(any())).thenReturn(getItInfosMockResponse());

		mockMvc
				.perform(get(BASE_URL, 0).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								IT_BASE_ID + "s",
								resource(
										ResourceSnippetParameters.builder()
												.description("잇 목록을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItInfosRequest"))
												.responseSchema(Schema.schema("ItInfosResponse"))
												.responseFields(Description.success(ItDescription.readIts()))
												.build())));
	}

	private ItInfos getItInfosMockResponse() {
		ItInfo it1 =
				ItInfo.builder()
						.id(1L)
						.topic("잇 주제")
						.startTime(LocalTime.of(1, 0))
						.endTime(LocalTime.of(2, 0))
						.inMemberCount(10L)
						.memberIn(true)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		ItInfo it2 =
				ItInfo.builder()
						.id(2L)
						.topic("잇 주제")
						.startTime(LocalTime.of(1, 0))
						.endTime(LocalTime.of(2, 0))
						.inMemberCount(10L)
						.memberIn(true)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		ItInfo it3 =
				ItInfo.builder()
						.id(3L)
						.topic("잇 주제")
						.startTime(LocalTime.of(1, 0))
						.endTime(LocalTime.of(2, 0))
						.inMemberCount(10L)
						.memberIn(false)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		return new ItInfos(List.of(it1, it2, it3));
	}

	private static final String INIT_BASE_ID = "InItInfo";

	@Test
	@DisplayName("[GET] " + BASE_URL + "/ins")
	void readInIts() throws Exception {
		// set service mock
		when(getInItsUseCase.execute(any())).thenReturn(getInItInfosMockResponse());

		mockMvc
				.perform(get(BASE_URL + "/ins", 0).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								INIT_BASE_ID + "s",
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇 목록을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("InItInfoRequest"))
												.responseSchema(Schema.schema("InItInfoResponse"))
												.responseFields(Description.success(ItDescription.readInIts()))
												.build())));
	}

	private InItInfos getInItInfosMockResponse() {
		InItInfo inIt1 =
				InItInfo.builder()
						.id(1L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.days("0000001")
						.inMemberCount(10L)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		InItInfo inIt2 =
				InItInfo.builder()
						.id(2L)
						.title("참여 잇 제목")
						.topic("참여 잇 주제")
						.startTime(LocalTime.of(7, 0))
						.endTime(LocalTime.of(9, 0))
						.days("0000001")
						.inMemberCount(10L)
						.type(ItTypeDetails.IT_REGISTERED.getValue())
						.build();
		return new InItInfos(List.of(inIt1, inIt2));
	}

	@Test
	@DisplayName("[GET] " + BASE_URL + "/ins/{id}")
	void browseInIt() throws Exception {
		// set service mock
		when(getInItUseCase.execute(any())).thenReturn(getInItInfoMockResponse());

		mockMvc
				.perform(get(BASE_URL + "/ins/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								INIT_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("InItInfoRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("InItInfoResponse"))
												.responseFields(Description.success(ItDescription.browseInIt()))
												.build())));
	}

	private InItInfo getInItInfoMockResponse() {
		return InItInfo.builder()
				.id(1L)
				.title("참여 잇 제목")
				.topic("참여 잇 주제")
				.startTime(LocalTime.of(7, 0))
				.endTime(LocalTime.of(9, 0))
				.days("000001")
				.inMemberCount(10L)
				.type(ItTypeDetails.IT_REGISTERED.getValue())
				.build();
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL + "/ins/{id}")
	void browseInIt_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(get(BASE_URL + "/ins/{id}", -1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								INIT_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇을 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("InItInfoRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("InItInfoResponse"))
												.responseFields(Description.fail())
												.build())));
	}

	private static final String IT_MOTIVATIONS_BASE_ID = "ItMotivations";

	@Test
	@DisplayName("[GET] " + BASE_URL + "/ins/{id}/motivations")
	void readItMotivations() throws Exception {
		// set service mock
		when(inItMotivationUseCase.execute(any())).thenReturn(getItMotivationsMockResponse());

		mockMvc
				.perform(get(BASE_URL + "/ins/{id}/motivations", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
						document(
								IT_MOTIVATIONS_BASE_ID,
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇 동기부여 정보를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItMotivationsRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("ItMotivationsResponse"))
												.responseFields(Description.success(ItDescription.readItMotivations()))
												.build())));
	}

	private ItMotivations getItMotivationsMockResponse() {
		List<String> motivations = List.of("motivation1", "motivation2");
		return new ItMotivations(motivations);
	}

	@Test
	@DisplayName("[GET/invalidId] " + BASE_URL + "/ins/{id}/motivations")
	void readItMotivations_invalidId() throws Exception {
		// set service mock

		mockMvc
				.perform(
						get(BASE_URL + "/ins/{id}/motivations", -1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andDo(
						document(
								IT_MOTIVATIONS_BASE_ID + "_invalidId",
								resource(
										ResourceSnippetParameters.builder()
												.description("참여 잇 동기부여 정보를 조회한다.")
												.tag(TAG)
												.requestSchema(Schema.schema("ItMotivationsRequest"))
												.pathParameters(parameterWithName("id").description("참여 잇 id"))
												.responseSchema(Schema.schema("ItMotivationsResponse"))
												.responseFields(Description.fail())
												.build())));
	}
}
