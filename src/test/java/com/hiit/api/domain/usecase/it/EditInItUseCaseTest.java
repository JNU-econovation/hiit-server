package com.hiit.api.domain.usecase.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.it.EditInItUseCaseRequest;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@ActiveProfiles(value = "test")
@SpringBootTest
@RecordApplicationEvents
class EditInItUseCaseTest {

	@Autowired private EditInItUseCase editInItUseCase;

	@Autowired private InItDao inItDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;
	@Autowired private InItInitializer inItInitializer;

	@Autowired private ApplicationEvents events;

	private EditInItUseCaseRequest request;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		RegisteredItEntity registeredIt = registeredItInitializer.getData();
		inItInitializer.initialize(registeredIt, member);
		InItEntity inIt = inItInitializer.getData();
		final Long memberId = member.getId();
		final Long inItId = inIt.getId();
		final String resolution = "다짐!!";
		request =
				EditInItUseCaseRequest.builder()
						.memberId(memberId)
						.inIt(inItId)
						.dayCode(DayCodeList.MON.getCode())
						.resolution(resolution)
						.build();
	}

	@Test
	void execute() {
		// given
		final Long itId = registeredItInitializer.getData().getId();
		final Long memberId = request.getMemberId();
		final String resolution = request.getResolution();

		// when
		editInItUseCase.execute(request);

		// then
		Optional<InItEntity> activeInIt = inItDao.findActiveStatusByItIdAndMember(itId, memberId);
		assertNotNull(activeInIt.get());
		InItEntity inIt = activeInIt.get();
		assertThat(inIt.getResolution()).isEqualTo(resolution);
		assertThat(inIt.getHiitMember().getId()).isEqualTo(memberId);
	}
}
