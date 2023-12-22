package com.hiit.api.domain.service.end.it;

import static com.hiit.api.domain.dao.it.relation.TargetItTypeInfo.REGISTERED_IT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.dao.it.registerd.RegisteredItData;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dao.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.service.manager.it.time.ItTimeInfoExecutorManager;
import com.hiit.api.domain.service.manager.it.time.RegisteredItTimeInfoExecutor;
import com.hiit.api.repository.RepositoryConfig;
import java.time.LocalTime;
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
@ContextConfiguration(
		classes = {
			EndItTimeInfoService.class,
			ItTimeInfoExecutorManager.class,
			RegisteredItTimeInfoExecutor.class,
			RepositoryConfig.class
		})
class EndItTimeInfoServiceTest {

	@InjectMocks @Autowired private EndItTimeInfoService endItTimeInfoService;

	@MockBean private ItRelationDao itRelationDao;

	@MockBean private RegisteredItDao registeredItDao;

	@Autowired private ItTimeInfoExecutorManager itTimeInfoExecutorManager;
	@Autowired private RegisteredItTimeInfoExecutor registeredItTimeInfoServiceExecutor;

	@Test
	@DisplayName("등록된 잇의 시간 정보를 조회한다.")
	void read_registered_type_it() {
		// given
		Long relationId = 1L;
		LocalTime startTime = LocalTime.of(7, 0);
		LocalTime endTime = LocalTime.of(9, 0);
		given(itRelationDao.findById(anyLong())).willReturn(setItRelationData(REGISTERED_IT));
		given(registeredItDao.findById(anyLong())).willReturn(setRegisteredItData(startTime, endTime));

		// when
		EndItTimeInfo res = endItTimeInfoService.read(relationId);

		// then
		assertThat(res.getStartTime()).isEqualTo(startTime);
		assertThat(res.getEndTime()).isEqualTo(endTime);
	}

	private Optional<ItRelationData> setItRelationData(TargetItTypeInfo typeInfo) {
		return Optional.of(
				ItRelationData.builder().targetId(1L).targetType(typeInfo).inItId(1L).build());
	}

	private Optional<RegisteredItData> setRegisteredItData(LocalTime startTime, LocalTime endTime) {
		return Optional.of(
				RegisteredItData.builder().topic("topic").startTime(startTime).endTime(endTime).build());
	}
}
