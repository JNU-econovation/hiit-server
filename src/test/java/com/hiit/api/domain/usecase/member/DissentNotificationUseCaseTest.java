package com.hiit.api.domain.usecase.member;

import static org.junit.jupiter.api.Assertions.*;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.DissentNotificationUseCaseRequest;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;

@ActiveProfiles(value = "test")
@SpringBootTest
@RecordApplicationEvents
class DissentNotificationUseCaseTest {

	@Autowired private DissentNotificationUseCase dissentNotificationUseCase;

	@Autowired private MemberDao memberDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;

	private DissentNotificationUseCaseRequest request;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		final Long memberId = member.getId();
		memberDao.save(member.toBuilder().notificationConsent(true).build());
		request = DissentNotificationUseCaseRequest.builder().memberId(memberId).build();
	}

	@Test
	void execute() {
		// given
		final Long memberId = request.getMemberId();

		// when
		dissentNotificationUseCase.execute(request);

		// then
		HiitMemberEntity member = memberDao.findById(memberId).get();
		assertNotNull(member);
		assertFalse(member.getNotificationConsent());
	}
}
