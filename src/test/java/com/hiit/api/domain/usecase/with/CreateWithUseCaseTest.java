package com.hiit.api.domain.usecase.with;

import static org.junit.jupiter.api.Assertions.*;

import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.with.CreateWithUseCaseRequest;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.init.it.InItInitializer;
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
class CreateWithUseCaseTest {

	@Autowired private CreateWithUseCase createWithUseCase;

	@Autowired private WithDao withDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;

	@Autowired private InItInitializer inItInitializer;

	@Autowired private ApplicationEvents events;

	private CreateWithUseCaseRequest request;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		RegisteredItEntity it = registeredItInitializer.getData();
		inItInitializer.initialize(it, member);
		InItEntity inIt = inItInitializer.getData();
		final Long memberId = member.getId();
		final Long inItId = inIt.getId();
		final String content = "내용!!";
		request =
				CreateWithUseCaseRequest.builder()
						.memberId(memberId)
						.inItId(inItId)
						.content(content)
						.build();
	}

	@Test
	void execute() {
		// given
		final Long memberId = request.getMemberId();
		final Long inItId = request.getInItId();
		final String content = request.getContent();

		// when
		createWithUseCase.execute(request);

		// then
		Long count = withDao.countByInIt(inItId);
		assertEquals(1, count);
		List<WithEntity> withs = withDao.findAllByInItAndMember(inItId, memberId);
		assertEquals(1, withs.size());
		WithEntity with = withs.get(0);
		assertEquals(content, with.getContent());
		assertEquals(inItId, with.getInIt().getId());
		assertEquals(memberId, with.getMemberId());
	}
}
