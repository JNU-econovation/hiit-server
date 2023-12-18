package com.hiit.api.domain.service.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

import com.hiit.api.domain.dao.it.in.DayCodeInfo;
import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.in.InItData;
import com.hiit.api.domain.dao.it.in.ItStatusInfo;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dao.it.relation.TargetItTypeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@ExtendWith(MockitoExtension.class)
class MemberRegisteredItRelationServiceTest {

	@InjectMocks private MemberRegisteredItRelationService memberRegisteredItRelationService;

	@Mock private InItDao initDao;

	@Mock private ItRelationDao itRelationDao;

	@Test
	@DisplayName("멤버가 참여중인 등록된 잇을 조회한다.")
	void browse() {
		// given
		Long memberId = 1L;
		int testDataSize = 5;

		given(initDao.findAllActiveStatusByMember(memberId)).willReturn(setInItTestData(testDataSize));
		// 테스트를 위한 임시 데이터를 설정한다.
		given(itRelationDao.findById(anyLong()))
				.willReturn(setItRelationTestData(TargetItTypeInfo.FOR_TEST));
		// 임시 데이터 중 홀수 번째 데이터만 등록된 잇으로 설정한다.
		given(itRelationDao.findById(argThat(arg -> arg % 2 == 1L)))
				.willReturn(setItRelationTestData(TargetItTypeInfo.REGISTERED_IT));

		// when
		List<ItRelationData> res = memberRegisteredItRelationService.browse(memberId);

		// then
		int registeredItRelationSize = testDataSize - (testDataSize / 2);
		assertThat(res).hasSize(registeredItRelationSize);
	}

	private Optional<ItRelationData> setItRelationTestData(TargetItTypeInfo typeInfo) {
		return Optional.of(
				ItRelationData.builder().id(1L).targetId(1L).targetType(typeInfo).inItId(1L).build());
	}

	private List<InItData> setInItTestData(int size) {
		List<InItData> inItDataList = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			inItDataList.add(
					InItData.builder()
							.id((long) i)
							.title("title")
							.resolution("resolution")
							.dayCode(DayCodeInfo.MON)
							.status(ItStatusInfo.ACTIVE)
							.memberId(1L)
							.itRelationId((long) i)
							.build());
		}
		return inItDataList;
	}
}
