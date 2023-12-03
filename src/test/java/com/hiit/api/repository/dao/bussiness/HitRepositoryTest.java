package com.hiit.api.repository.dao.bussiness;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hiit.api.repository.AbstractRepositoryTest;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.exception.InvalidParamException;
import com.hiit.api.repository.init.it.InItInitializer;
import com.hiit.api.repository.init.it.RegisteredItInitializer;
import com.hiit.api.repository.init.member.HiitMemberInitializer;
import com.hiit.api.repository.init.with.WithInitializer;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class HitRepositoryTest extends AbstractRepositoryTest {

	@Autowired HitRepository repository;

	@Autowired HiitMemberInitializer hiitMemberInitializer;
	@Autowired RegisteredItInitializer registeredItInitializer;
	@Autowired InItInitializer inItInitializer;
	@Autowired WithInitializer withInitializer;

	@BeforeEach
	void setUp() {
		hiitMemberInitializer.initialize();
		registeredItInitializer.initialize();
		inItInitializer.initialize(registeredItInitializer.getData(), hiitMemberInitializer.getData());
		withInitializer.initialize(inItInitializer.getData());
	}

	@Test
	@DisplayName("익명 사용자가 힛을 hit 했을 때, hitter가 익명 사용자인지 확인한다")
	void hit_by_anonymous_test() {
		// given
		WithEntity with = withInitializer.getData();
		Hitter hitter = Hitter.anonymous();
		HitEntity hit = HitEntity.builder().status(HitStatus.HIT).withEntity(with).build();

		// when
		HitEntity result = repository.save(hit);

		// then
		assertThat(result.getHitter()).isEqualTo(hitter);
	}

	@Test
	@DisplayName("회원이 힛을 hit 했을 때, hitter가 회원인지 확인한다")
	void hit_by_member_test() {
		// given
		WithEntity with = withInitializer.getData();
		Hitter hitter = Hitter.of(hiitMemberInitializer.getData().getId());
		HitEntity hit =
				HitEntity.builder().status(HitStatus.HIT).hitter(hitter).withEntity(with).build();

		// when
		HitEntity result = repository.save(hit);

		// then
		assertThat(result.getHitter()).isEqualTo(hitter);
	}

	@Test
	@DisplayName("힛이 hit인 상태에서 hit 했을 때, 상태가 miss로 변경되는지 확인한다")
	void hit_to_miss_test() {
		// given
		WithEntity with = withInitializer.getData();
		HitEntity hit = HitEntity.builder().status(HitStatus.HIT).withEntity(with).build();
		HitEntity saved = repository.save(hit);

		// when
		HitStatus result = saved.changeStatus().getStatus();

		// then
		assertThat(result).isEqualTo(HitStatus.MISS);
	}

	@Test
	@DisplayName("힛이 miss인 상태에서 hit 했을 때, 상태가 hit으로 변경되는지 확인한다")
	void miss_to_hit_test() {
		// given
		WithEntity with = withInitializer.getData();
		HitEntity hit = HitEntity.builder().status(HitStatus.MISS).withEntity(with).build();
		HitEntity saved = repository.save(hit);

		// when
		HitStatus result = saved.changeStatus().getStatus();

		// then
		assertThat(result).isEqualTo(HitStatus.HIT);
	}

	@Test
	@DisplayName("특정 시간 사이에 윗에 생성된 Hit 개수를 조회한다.")
	void countByWithEntityAndStatusAndCreateAtBetween() {
		// given
		LocalDateTime start = LocalDateTime.now().minusMinutes(1);
		LocalDateTime end = LocalDateTime.now().plusMinutes(1);
		WithEntity with = withInitializer.getData();
		int size = 5;
		setAdditionalData(size, with);

		// when
		Long result =
				repository.countByWithEntityAndStatusAndCreateAtBetween(with, HitStatus.HIT, start, end);

		// then
		assertThat(result).isEqualTo(size);
	}

	@Test
	@DisplayName("특정 시간 사이에 특정 윗에 대해 특정 힛터가 생성한 Hit을 조회한다.")
	void findByWithEntityAndHitterAndStatusAndCreateAtBetween() {
		// given
		LocalDateTime start = LocalDateTime.now().minusMinutes(1);
		LocalDateTime end = LocalDateTime.now().plusMinutes(1);
		HiitMemberEntity hiitMember = hiitMemberInitializer.getData();
		Hitter hitter = Hitter.of(hiitMember.getId());
		WithEntity with = withInitializer.getData();
		HitEntity hit =
				HitEntity.builder().status(HitStatus.HIT).withEntity(with).hitter(hitter).build();
		repository.save(hit);
		int size = 5;
		setAdditionalData(size, hiitMember, with);

		// when
		Optional<HitEntity> result =
				repository.findByWithEntityAndHitterAndStatusAndCreateAtBetween(
						with, hitter, HitStatus.HIT, start, end);

		// then
		assertThat(result).contains(hit);
	}

	@Test
	@DisplayName("특정 시간 사이에 특정 윗에 대해 익명 힛터가 생성한 Hit을 조회할 수 없다.")
	void findByWithEntityAndHitterAndStatusAndCreateAtBetween_Anonymous() {
		// given
		LocalDateTime start = LocalDateTime.now().minusMinutes(1);
		LocalDateTime end = LocalDateTime.now().plusMinutes(1);
		Hitter hitter = Hitter.anonymous();
		WithEntity with = withInitializer.getData();
		HitEntity hit = HitEntity.builder().status(HitStatus.HIT).withEntity(with).build();
		repository.save(hit);

		// when & then
		assertThrows(
				InvalidParamException.class,
				() ->
						repository.findByWithEntityAndHitterAndStatusAndCreateAtBetween(
								with, hitter, HitStatus.HIT, start, end));
	}

	@Test
	@DisplayName("특정 시간 사이에 특정 잇에 대해 생성된 Hit 개수를 조회한다.")
	void countByInItEntityAndStatusAndCreateAtBetween() {
		// given
		LocalDateTime start = LocalDateTime.now().minusMinutes(1);
		LocalDateTime end = LocalDateTime.now().plusMinutes(1);
		InItEntity init = inItInitializer.getData();
		WithEntity with = withInitializer.getData();
		HitEntity hit = HitEntity.builder().status(HitStatus.HIT).withEntity(with).build();
		repository.save(hit);
		InItEntity otherInIt =
				inItInitializer.addData(hiitMemberInitializer.getData(), registeredItInitializer.getData());
		WithEntity otherWith = withInitializer.addData(otherInIt);
		int size = 5;
		setAdditionalData(size, otherWith);

		// when
		Long result =
				repository.countByInItEntityAndStatusAndCreateAtBetween(init, HitStatus.HIT, start, end);
		Long otherResult =
				repository.countByInItEntityAndStatusAndCreateAtBetween(
						otherInIt, HitStatus.HIT, start, end);

		// then
		assertThat(result).isEqualTo(1);
		assertThat(otherResult).isEqualTo(size);
	}

	private void setAdditionalData(int size, WithEntity with) {
		for (int i = 0; i < size; i++) {
			Hitter hitter = Hitter.of((long) i);
			HitEntity hit =
					HitEntity.builder().hitter(hitter).status(HitStatus.HIT).withEntity(with).build();
			repository.save(hit);
		}
	}

	private void setAdditionalData(int size, HiitMemberEntity hiitMember, WithEntity with) {
		for (int i = hiitMember.getId().intValue(); i < hiitMember.getId() + size; i++) {
			Hitter otherHitter = Hitter.of((long) i);
			HitEntity otherHit =
					HitEntity.builder().status(HitStatus.HIT).withEntity(with).hitter(otherHitter).build();
			repository.save(otherHit);
		}
	}
}
