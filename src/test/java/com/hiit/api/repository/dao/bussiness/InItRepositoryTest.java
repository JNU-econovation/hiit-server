package com.hiit.api.repository.dao.bussiness;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hiit.api.repository.AbstractRepositoryTest;
import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class InItRepositoryTest extends AbstractRepositoryTest {

	@Autowired EntityManager em;

	@Autowired InItRepository repository;
	@Autowired ItRelationRepository itRelationRepository;

	@Autowired HiitMemberInitializer hiitMemberInitializer;

	@Autowired RegisteredItInitializer registeredItInitializer;
	@Autowired InItInitializer inItInitializer;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		inItInitializer.initialize(registeredItInitializer.getData(), hiitMemberInitializer.getData());
	}

	@Test
	@DisplayName("특정 멤버가 가진 특정 상태의 모든 참여 잇을 조회한다.")
	void findAllByHiitMemberAndStatus() {
		// given
		HiitMemberEntity member = hiitMemberInitializer.getData();
		ItStatus status = ItStatus.ACTIVE;
		int size = 5;
		setAdditionalData(size, member);

		// when
		List<InItEntity> result = repository.findAllByHiitMemberAndStatus(member, status);

		// then
		assertEquals(size + 1, result.size());
	}

	@Test
	@DisplayName("특정 아이디 값과 특정 멤버를 가진 특정 상태의 잇을 조회한다.")
	void findByIdAndHiitMemberAndStatus() {
		// given
		InItEntity source = inItInitializer.getData();
		HiitMemberEntity member = hiitMemberInitializer.getData();
		ItStatus status = ItStatus.ACTIVE;

		// when
		InItEntity result =
				repository.findByIdAndHiitMemberAndStatus(source.getId(), member, status).orElseThrow();

		// then
		assertEquals(source, result);
	}

	@Test
	@DisplayName("특정 참여 잇과 연관된 잇 연관 엔티티를 모두 삭제한다.")
	void deleteByIdWithItRelation() {
		// given
		Long id = inItInitializer.getData().getId();

		// when
		repository.deleteByIdWithItRelation(id);
		em.clear();

		// then
		assertTrue(repository.findById(id).isEmpty());
		assertEquals(0, itRelationRepository.countByTargetItId(id));
	}

	@Test
	@DisplayName("특정 타겟 아이디와 특정 상태, 특정 요일 코드를 가진 모든 잇을 조회한다.")
	void findAllByTargetIdAndDayCode() {
		// given
		Long targetId = registeredItInitializer.getData().getId();
		DayCodeList dayCode = inItInitializer.getDayCode();
		int size = 5;
		HiitMemberEntity member = hiitMemberInitializer.getData();
		setAdditionalData(size, member);
		DayCodeList otherDayCode = DayCodeList.FRI;
		inItInitializer.addData(member, registeredItInitializer.getData(), otherDayCode);

		// when
		List<InItEntity> result =
				repository.findAllByTargetIdAndStatusAndDayCode(targetId, ItStatus.ACTIVE, dayCode);
		List<InItEntity> otherResult =
				repository.findAllByTargetIdAndStatusAndDayCode(targetId, ItStatus.ACTIVE, otherDayCode);

		// then
		assertEquals(size + 1, result.size());
		assertEquals(1, otherResult.size());
	}

	@Test
	@DisplayName("특정 타겟 아이디와 특정 상태, 특정 멤버를 가진 잇을 조회한다.")
	void findTopByTargetIdAndHiitMember() {
		// given
		InItEntity source = inItInitializer.getData();
		Long targetId = registeredItInitializer.getData().getId();
		HiitMemberEntity member = hiitMemberInitializer.getData();

		// when
		Optional<InItEntity> result =
				repository.findByTargetIdAndStatusAndHiitMember(targetId, ItStatus.ACTIVE, member);

		// then
		assertThat(result).contains(source);
	}

	private void setAdditionalData(int size, HiitMemberEntity member) {
		for (int i = 0; i < size; i++) {
			inItInitializer.addData(member, registeredItInitializer.getData());
		}
	}
}
