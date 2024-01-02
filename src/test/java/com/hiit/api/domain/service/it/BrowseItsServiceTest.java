package com.hiit.api.domain.service.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.hiit.api.AppMain;
import com.hiit.api.domain.dao.it.registerd.RegisteredItDao;
import com.hiit.api.domain.model.it.registered.BasicIt;
import com.hiit.api.domain.usecase.it.RegisteredItEntityConverter;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
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
class BrowseItsServiceTest {

	@InjectMocks @Autowired private BrowseItsService browseItsService;

	@MockBean private RegisteredItDao registeredItDao;

	@Autowired private RegisteredItEntityConverter registeredItEntityConverter;

	@Autowired private JsonConverter jsonConverter;

	@Test
	@DisplayName("등록된 잇을 조회한다.")
	void browse() {
		// given
		int testDataSize = 5;
		when(registeredItDao.findAll()).thenReturn(setRegisteredIt(testDataSize));

		// when
		List<BasicIt> its = browseItsService.browse();

		// then
		assertThat(its).hasSize(testDataSize);
		for (int i = 0; i < testDataSize; i++) {
			assertThat(its.get(i).getTopic()).contains("registered");
		}
	}

	@Test
	@DisplayName("특정 등록된 잇을 조회한다.")
	void browse_itId() {
		// given
		when(registeredItDao.findById(anyLong())).thenReturn(Optional.of(setRegisteredIt(1).get(0)));

		// when
		BasicIt it = browseItsService.browse(1L);

		// then
		assertThat(it.getTopic()).contains("registered");
		assertThat(it.getId()).isEqualTo(1L);
	}

	private List<RegisteredItEntity> setRegisteredIt(int size) {
		List<RegisteredItEntity> registeredItList = new ArrayList<>();
		LocalTime now = LocalTime.now();
		LocalTime startTime = now.minusHours(1);
		LocalTime endTime = now.plusHours(1);
		for (int i = 1; i <= size; i++) {
			registeredItList.add(
					RegisteredItEntity.builder()
							.id((long) i)
							.topic("registered" + i)
							.startTime(startTime)
							.endTime(endTime)
							.build());
		}
		return registeredItList;
	}
}
