package com.hiit.api.domain.usecase.end.it;

import static org.assertj.core.api.Assertions.assertThat;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dto.request.end.DeleteEndItUseCaseRequest;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles(value = "test")
@SpringBootTest
@RecordApplicationEvents
class DeleteEndItUseCaseTest {

	@Autowired private DeleteEndItUseCase deleteEndItUseCase;

	@Autowired private InItDao inItDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;

	@Autowired private InItInitializer inItInitializer;

	@Autowired private EntityManager entityManager;

	@Autowired private ApplicationEvents events;

	private DeleteEndItUseCaseRequest request;

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
		request = DeleteEndItUseCaseRequest.builder().memberId(memberId).endInItId(inItId).build();
	}

	@Test
	@Transactional
	void execute() {
		// given
		final Long memberId = request.getMemberId();
		final Long endInItId = request.getEndInItId();
		inItDao.endByIdWithItRelation(endInItId, "끝!!");

		// when
		deleteEndItUseCase.execute(request);

		// then
		int active = inItDao.findAllActiveStatusByMember(memberId).size();
		int end = inItDao.findAllEndStatusByMember(memberId).size();
		assertThat(active).isZero();
		assertThat(end).isZero();
	}
}
