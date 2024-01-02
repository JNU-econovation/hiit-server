package com.hiit.api.repository.dao.bussiness;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.hiit.api.repository.AbstractRepositoryTest;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMembersInitializer;
import com.hiit.api.repository.init.with.WithInitializer;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class WithRepositoryTest extends AbstractRepositoryTest {

	@Autowired WithRepository repository;

	@Autowired HiitMembersInitializer hiitMembersInitializer;
	@Autowired RegisteredItInitializer registeredItInitializer;
	@Autowired InItInitializer inItInitializer;
	@Autowired WithInitializer withInitializer;

	@BeforeEach
	void setUp() {
		hiitMembersInitializer.initialize(1);
		registeredItInitializer.initialize();
		inItInitializer.initialize(
				registeredItInitializer.getData(), hiitMembersInitializer.getData().get(0));
		withInitializer.initialize(inItInitializer.getData(), hiitMembersInitializer.getData().get(0));
	}

	@Test
	@DisplayName("특정 잇의 with 개수를 반환한다.")
	void countByInItId() {
		// given
		InItEntity init = inItInitializer.getData();

		// when
		Long count = repository.countByInIt(init);

		// then
		assertThat(count).isEqualTo(1);
	}

	@Test
	@DisplayName("특정 잇과 페이징 정보 그리고 특정 멤버 필터링 하지 않은 페이징 처리된 with 목록을 반환한다.")
	void findAllByInIt() {
		// given
		InItEntity init = inItInitializer.getData();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = now.minusMinutes(1);
		LocalDateTime end = now.plusMinutes(1);

		// when
		Page<WithEntity> page = repository.findAllByInIt(init, PageRequest.of(0, 10), null, start, end);

		// then
		WithEntity content = page.getContent().get(0);
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(content.getInIt()).isEqualTo(init);
	}

	@Test
	@DisplayName("특정 잇과 페이징 정보 그리고 특정 멤버 필터링 한 페이징 처리된 with 목록을 반환한다.")
	void findAllByInIt_filter_by_member() {
		// given
		InItEntity init = inItInitializer.getData();
		HiitMemberEntity hiitMember = hiitMembersInitializer.getData().get(0);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime start = now.minusMinutes(1);
		LocalDateTime end = now.plusMinutes(1);

		// when
		Page<WithEntity> page =
				repository.findAllByInIt(init, PageRequest.of(0, 10), hiitMember, start, end);

		// then
		WithEntity content = page.getContent().get(0);
		HiitMemberEntity member = content.getInIt().getHiitMember();
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(content.getInIt()).isEqualTo(init);
		assertThat(member).isEqualTo(hiitMember);
	}

	@Test
	@DisplayName("특정 잇과 특정 멤버를 가진 with 목록을 반환한다.")
	void findAllByInIt_list() {
		// given
		InItEntity init = inItInitializer.getData();
		HiitMemberEntity hiitMember = hiitMembersInitializer.getData().get(0);

		// when
		List<WithEntity> withs = repository.findAllByInIt(init, hiitMember);

		// then
		assertThat(withs.size()).isEqualTo(1);
		assertThat(withs.get(0).getInIt()).isEqualTo(init);
	}

	@Test
	@DisplayName("특정 잇과 특정 멤버 그리고 특정 시간 사이에 생성된 with를 반환한다.")
	void findByInItEntityAndHiitMemberAndCreateAtBetween() {
		// given
		LocalDateTime now = LocalDateTime.now();
		InItEntity init = inItInitializer.getData();
		HiitMemberEntity hiitMember = hiitMembersInitializer.getData().get(0);

		// when
		LocalDateTime end = LocalDateTime.now();
		List<WithEntity> withs =
				repository.findAllByInItEntityAndHiitMemberAndCreateAtBetween(
						init, hiitMember, now.minusMinutes(1), end);
		WithEntity with = withs.get(0);

		// then
		assertThat(with.getInIt()).isEqualTo(init);
		assertThat(with.getInIt().getHiitMember()).isEqualTo(hiitMember);
	}

	@Test
	@DisplayName("특정 윗이 존재하는지 여부를 반환한다.")
	void existsById() {
		// given
		WithEntity with = withInitializer.getData();

		// when
		boolean exists = repository.existsById(with.getId());

		// then
		assertThat(exists).isTrue();
	}
}
