package com.hiit.api.domain.usecase.member.listener;

import static com.hiit.api.repository.entity.business.it.ItType.REGISTERED_IT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.usecase.with.event.CreateWithEvent;
import com.hiit.api.repository.document.member.ItWithStats;
import com.hiit.api.repository.document.member.MemberStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import com.hiit.api.repository.init.with.WithInitializer;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;

@ActiveProfiles(value = "test")
@SpringBootTest
@RecordApplicationEvents
class UpdateItWithStatsCreateWithEventListenerTest {

	@Autowired private UpdateItWithStatsEventListener listener;

	@Autowired private MemberDao memberDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;
	@Autowired private InItInitializer inItInitializer;
	@Autowired private WithInitializer withInitializer;

	private CreateWithEvent event;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		RegisteredItEntity registeredIt = registeredItInitializer.getData();
		inItInitializer.initialize(registeredIt, member);
		InItEntity inIt = inItInitializer.getData();
		withInitializer.initialize(inIt, member);
		WithEntity with = withInitializer.getData();
		final Long memberId = member.getId();
		final Long inItId = inIt.getId();
		final Long withId = with.getId();
		ItWithStats itWithStats = new ItWithStats(Map.of(inItId, Map.of(REGISTERED_IT.getType(), 0L)));
		MemberStat memberStat =
				MemberStat.builder()
						.memberId(memberId)
						.totalItCount(1L)
						.itWithCountStats(itWithStats)
						.build();
		MemberStatDoc doc = MemberStatDoc.builder().memberId(memberId).resource(memberStat).build();
		memberDao.saveMemberStatDoc(doc);
		event = CreateWithEvent.builder().memberId(memberId).inItId(inItId).withId(withId).build();
	}

	@Test
	@DisplayName("이벤트를 통해 윗을 생성시 회원 통계 문서 업데이트")
	void handle() {
		Long memberId = event.getMemberId();
		Long inItId = event.getInItId();
		listener.handle(event);
		MemberStatDoc docs = memberDao.findMemberStatDocByMemberId(memberId).orElse(null);

		assertNotNull(docs);
		assertThat(docs.getMemberId()).isEqualTo(memberId);
		MemberStat resource = docs.getResource();
		assertThat(resource.getMemberId()).isEqualTo(memberId);
		assertThat(resource.getTotalWithCount()).isOne();
		assertThat(resource.getItWithCountStats().get(inItId).get(REGISTERED_IT.getType())).isOne();
	}
}
