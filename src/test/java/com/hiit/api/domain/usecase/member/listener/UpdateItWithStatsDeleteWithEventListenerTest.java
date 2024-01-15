package com.hiit.api.domain.usecase.member.listener;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.usecase.with.event.DeleteWithEvent;
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
class UpdateItWithStatsDeleteWithEventListenerTest {

	@Autowired private UpdateItWithStatsEventListener listener;

	@Autowired private MemberDao memberDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;
	@Autowired private InItInitializer inItInitializer;
	@Autowired private WithInitializer withInitializer;

	private DeleteWithEvent event;

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
		ItWithStats itWithStats = new ItWithStats(Map.of(inItId, 1L));
		MemberStat memberStat =
				MemberStat.builder()
						.memberId(memberId)
						.totalItCount(1L)
						.totalWithCount(1L)
						.itWithCountStats(itWithStats)
						.build();
		MemberStatDoc doc = MemberStatDoc.builder().memberId(memberId).resource(memberStat).build();
		memberDao.saveMemberStatDoc(doc);
		event = DeleteWithEvent.builder().memberId(memberId).inItId(inItId).build();
	}

	@Test
	@DisplayName("이벤트를 통해 윗을 삭제시 회원 통계 문서 수정")
	void handle() {
		Long memberId = event.getMemberId();
		Long inItId = event.getInItId();
		listener.handle(event);
		MemberStatDoc docs = memberDao.findMemberStatDocByMemberId(memberId).orElse(null);

		assertNotNull(docs);
		assertThat(docs.getMemberId()).isEqualTo(memberId);
		MemberStat resource = docs.getResource();
		assertThat(resource.getMemberId()).isEqualTo(memberId);
		assertThat(resource.getTotalItCount()).isOne();
		assertThat(resource.getTotalWithCount()).isZero();
		assertThat(resource.getItWithCountStats().get(inItId)).isZero();
	}
}