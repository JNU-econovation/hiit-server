package com.hiit.api.domain.usecase.end.it;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hiit.api.domain.dao.it.in.InItDao;
import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dto.request.end.EditEndItUseCaseRequest;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import java.util.Optional;
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
class EditEndItUseCaseTest {

	@Autowired private EditEndItUseCase editEndItUseCase;

	@Autowired private InItDao inItDao;

	@Autowired private ItRelationDao itRelationDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;

	@Autowired private InItInitializer inItInitializer;

	@Autowired private EntityManager entityManager;

	@Autowired private ApplicationEvents events;

	private EditEndItUseCaseRequest request;

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
		final String title = "제목 변경!";
		request =
				EditEndItUseCaseRequest.builder().memberId(memberId).endInItId(inItId).title(title).build();
	}

	@Test
	@Transactional
	void execute() {
		// given
		final Long memberId = request.getMemberId();
		final Long inItId = request.getEndInItId();
		final String title = request.getTitle();
		ItRelationEntity itRelation =
				itRelationDao.findByInItIdAndStatus(inItId, ItStatus.ACTIVE).orElse(null);
		assert itRelation != null;
		inItDao.endById(inItId, title);
		itRelationDao.endById(itRelation.getId());
		entityManager.flush();
		entityManager.clear();

		// when
		editEndItUseCase.execute(request);

		// then
		Optional<InItEntity> source = inItDao.findById(inItId);
		assertNotNull(source);
		InItEntity inIt = source.get();
		assertThat(inIt.getTitle()).isEqualTo(title);
	}
}
