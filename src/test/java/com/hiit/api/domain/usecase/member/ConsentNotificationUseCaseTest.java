package com.hiit.api.domain.usecase.member;

import static org.junit.jupiter.api.Assertions.*;

import com.hiit.api.domain.dao.member.MemberDao;
import com.hiit.api.domain.dto.request.member.ConsentNotificationUseCaseRequest;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.member.MemberNotificationInfoEntity;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.RecordApplicationEvents;

@ActiveProfiles(value = "test")
@SpringBootTest
@RecordApplicationEvents
class ConsentNotificationUseCaseTest {

	@Autowired private ConsentNotificationUseCase consentNotificationUseCase;

	@Autowired private MemberDao memberDao;

	@Autowired private HiitMemberInitializer hiitMemberInitializer;

	@Autowired private RegisteredItInitializer registeredItInitializer;
	private ConsentNotificationUseCaseRequest request;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		final Long memberId = member.getId();
		final String device = "device";
		request = ConsentNotificationUseCaseRequest.builder().device(device).memberId(memberId).build();
	}

	@Test
	void execute() {
		// given
		final Long memberId = request.getMemberId();
		final String device = request.getDevice();

		// when
		consentNotificationUseCase.execute(request);

		// then
		HiitMemberEntity member = memberDao.findById(memberId).get();
		assertNotNull(member);
		assertTrue(member.getNotificationConsent());
		List<String> devices =
				memberDao
						.findAllNotificationInfoByHiitMemberEntity(
								HiitMemberEntity.builder().id(memberId).build())
						.stream()
						.map(MemberNotificationInfoEntity::getDevice)
						.collect(Collectors.toList());
		assertTrue(devices.contains(device));
	}
}
