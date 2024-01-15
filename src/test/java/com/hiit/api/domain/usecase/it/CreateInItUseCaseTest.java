package com.hiit.api.domain.usecase.it;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.it.CreateInItUseCaseRequest;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import java.util.List;
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
class CreateInItUseCaseTest {

	@Autowired private CreateInItUseCase createInItUseCase;

	@Autowired private InItDao inItDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;

	@Autowired private ApplicationEvents events;

	private CreateInItUseCaseRequest request;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		RegisteredItEntity it = registeredItInitializer.getData();
		final Long memberId = member.getId();
		final Long itId = it.getId();
		final String dayCode = DayCodeList.MON.toString();
		final String resolution = "다짐!!";
		final ItTypeDetails type = ItTypeDetails.IT_REGISTERED;
		request =
				CreateInItUseCaseRequest.builder()
						.memberId(memberId)
						.itId(itId)
						.dayCode(dayCode)
						.resolution(resolution)
						.type(type)
						.build();
	}

	@Test
	void execute() {
		// given
		final Long memberId = request.getMemberId();
		final String dayCode = request.getDayCode();
		final String resolution = request.getResolution();

		// when
		createInItUseCase.execute(request);

		// then
		List<InItEntity> sources = inItDao.findAllActiveStatusByMember(memberId);
		InItEntity source = sources.get(0);
		assertThat(source.getResolution()).isEqualTo(resolution);
		assertThat(source.getDayCode()).isEqualTo(DayCodeList.valueOf(dayCode));
		assertThat(source.getHiitMember().getId()).isEqualTo(memberId);
	}

	@Test
	void execute_contain() {
		// given
		final Long memberId = request.getMemberId();
		final String dayCode = request.getDayCode();
		final String resolution = request.getResolution();

		// when
		for (int i = 0; i < 3; i++) {
			createInItUseCase.execute(request);
		}

		// then
		List<InItEntity> sources = inItDao.findAllActiveStatusByMember(memberId);
		assertThat(sources).hasSize(1);
		InItEntity source = sources.get(0);
		assertThat(source.getResolution()).isEqualTo(resolution);
		assertThat(source.getDayCode()).isEqualTo(DayCodeList.valueOf(dayCode));
		assertThat(source.getHiitMember().getId()).isEqualTo(memberId);
	}
}
