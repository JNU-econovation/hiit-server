package com.hiit.api.domain.service.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiit.api.AppMain;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.model.it.relation.ItRelation;
import com.hiit.api.domain.support.entity.converter.in.it.InItEntityConverterImpl;
import com.hiit.api.domain.support.entity.converter.in.relation.ItRelationEntityConverterImpl;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.it.TargetItType;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles(value = "test")
@SpringBootTest
@ContextConfiguration(classes = {AppMain.class})
class BrowseMemberInItRelationServiceTest {

	@InjectMocks @Autowired private BrowseMemberInItRelationService browseRegisteredItRelationService;

	@MockBean private InItDao initDao;

	@MockBean private ItRelationDao itRelationDao;

	@Autowired private InItEntityConverterImpl inItEntityConverter;

	@Autowired private ItRelationEntityConverterImpl itRelationEntityConverter;

	@Autowired private JsonConverter jsonConverter;

	@Autowired private ObjectMapper objectMapper;

	@Test
	@DisplayName("멤버가 참여중인 등록된 잇을 조회한다.")
	void browse() {
		// given
		Long memberId = 1L;
		int testDataSize = 5;

		when(initDao.findAllActiveStatusByMember(memberId)).thenReturn(setInItTestData(testDataSize));
		when(itRelationDao.findById(anyLong()))
				.thenAnswer(
						invocation -> {
							Long argument = (Long) invocation.getArguments()[0];
							if (argument % 2 == 1L) {
								return setItRelationTestData(TargetItType.REGISTERED_IT);
							}
							return setItRelationTestData(TargetItType.FOR_TEST);
						});
		// when
		List<ItRelation> res = browseRegisteredItRelationService.browse(memberId);

		// then
		int registeredItRelationSize = testDataSize - (testDataSize / 2);
		assertThat(res).hasSize(registeredItRelationSize);
	}

	private Optional<ItRelationEntity> setItRelationTestData(TargetItType typeInfo) {
		return Optional.of(
				ItRelationEntity.builder()
						.id(1L)
						.targetItId(1L)
						.targetItType(typeInfo)
						.inIt(InItEntity.builder().id(1L).build())
						.build());
	}

	private List<InItEntity> setInItTestData(int size) {
		List<InItEntity> inItDataList = new ArrayList<>();
		LocalTime now = LocalTime.now();
		LocalTime startTime = now.minusHours(1);
		LocalTime endTime = now.plusHours(1);
		String info = "{\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\"}";
		for (int i = 1; i <= size; i++) {
			inItDataList.add(
					InItEntity.builder()
							.id((long) i)
							.title("title")
							.resolution("resolution")
							.dayCode(DayCodeList.MON)
							.status(ItStatus.ACTIVE)
							.info(info)
							.hiitMember(HiitMemberEntity.builder().id(1L).build())
							.itRelationEntity(ItRelationEntity.builder().id((long) i).build())
							.build());
		}
		return inItDataList;
	}
}
