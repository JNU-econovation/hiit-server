package com.hiit.api.domain.usecase.with;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiit.api.domain.dao.with.WithDao;
import com.hiit.api.domain.dto.request.with.DeleteWithUseCaseRequest;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.entity.business.with.WithStatus;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import com.hiit.api.repository.init.with.WithInitializer;
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
class DeleteWithUseCaseTest {

	@Autowired private DeleteWithUseCase deleteWithUseCase;
	@Autowired private WithDao withDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;

	@Autowired private InItInitializer inItInitializer;

	@Autowired private WithInitializer withInitializer;

	@Autowired private ApplicationEvents events;

	private DeleteWithUseCaseRequest request;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		RegisteredItEntity it = registeredItInitializer.getData();
		inItInitializer.initialize(it, member);
		InItEntity inIt = inItInitializer.getData();
		withInitializer.initialize(inIt, member);
		WithEntity with = withInitializer.getData();
		final Long memberId = member.getId();
		request = DeleteWithUseCaseRequest.builder().memberId(memberId).withId(with.getId()).build();
	}

	@Test
	void execute() {
		// given
		final Long withId = request.getWithId();
		// when
		deleteWithUseCase.execute(request);
		// then
		boolean exist = withDao.existsByIdAndStatus(withId, WithStatus.ACTIVE);
		assertThat(exist).isFalse();
	}
}
