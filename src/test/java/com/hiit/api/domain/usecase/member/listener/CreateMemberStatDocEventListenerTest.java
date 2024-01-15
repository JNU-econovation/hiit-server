package com.hiit.api.domain.usecase.member.listener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.usecase.member.event.CreateMemberEvent;
import com.hiit.api.repository.document.member.MemberStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
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
class CreateMemberStatDocEventListenerTest {

	@Autowired private CreateMemberStatDocEventListener listener;

	@Autowired private MemberDao memberDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;
	private CreateMemberEvent event;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		Long memberId = hiitMemberInitializer.getData().getId();
		event = CreateMemberEvent.builder().memberId(memberId).build();
	}

	@Test
	@DisplayName("이벤트를 통해 회원 가입시 회원 통계 문서 생성")
	void handle() {
		Long memberId = event.getMemberId();
		listener.handle(event);
		MemberStatDoc docs = memberDao.findMemberStatDocByMemberId(memberId).orElse(null);

		assertNotNull(docs);
		assertThat(docs.getMemberId()).isEqualTo(memberId);
		MemberStat resource = docs.getResource();
		assertThat(resource.getMemberId()).isEqualTo(memberId);
		assertThat(resource.getTotalItCount()).isZero();
		assertThat(resource.getTotalWithCount()).isZero();
		assertThat(resource.getItWithCountStats()).isEmpty();
	}
}
