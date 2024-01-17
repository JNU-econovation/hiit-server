package com.hiit.api.repository.dao.document;

import static com.hiit.api.repository.entity.business.it.ItType.REGISTERED_IT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hiit.api.repository.AbstractRepositoryTest;
import com.hiit.api.repository.document.member.ItWithStat;
import com.hiit.api.repository.document.member.ItWithStats;
import com.hiit.api.repository.document.member.MemberStat;
import com.hiit.api.repository.document.member.MemberStatDoc;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberStatDocRepositoryTest extends AbstractRepositoryTest {

	@Autowired MemberStatDocRepository repository;

	@Autowired EntityManager entityManager;

	@Test
	@DisplayName("회원 통계를 저장한다")
	void save() {
		// given
		HiitMemberEntity member = HiitMemberEntity.builder().id(1L).build();
		ItWithStats itWithStats =
				new ItWithStats(
						Map.of(
								1L,
								Map.of(REGISTERED_IT.getType(), 10L),
								2L,
								Map.of(REGISTERED_IT.getType(), 20L)));
		MemberStat memberStat =
				MemberStat.builder()
						.memberId(member.getId())
						.totalItCount(10L)
						.totalWithCount(20L)
						.itWithCountStats(itWithStats)
						.build();
		MemberStatDoc source =
				MemberStatDoc.builder().id(member.getId().toString()).resource(memberStat).build();

		// when
		MemberStatDoc result = repository.save(source);

		// then
		assertEquals(source, result);
	}

	@Test
	@DisplayName("회원 통계를 수정한다")
	void update() {
		// given
		HiitMemberEntity member = HiitMemberEntity.builder().id(1L).build();
		ItWithStats itWithStats =
				new ItWithStats(
						Map.of(
								1L,
								Map.of(REGISTERED_IT.getType(), 10L),
								2L,
								Map.of(REGISTERED_IT.getType(), 20L)));
		MemberStat memberStat =
				MemberStat.builder()
						.memberId(member.getId())
						.totalItCount(10L)
						.totalWithCount(20L)
						.itWithCountStats(itWithStats)
						.build();
		MemberStatDoc docs =
				MemberStatDoc.builder().id(member.getId().toString()).resource(memberStat).build();
		MemberStatDoc source = repository.saveAndFlush(docs);
		entityManager.clear();
		entityManager.close();

		// when
		MemberStatDoc origin = repository.findById(Objects.requireNonNull(source.getId())).orElse(null);
		MemberStat originMemberStat = Objects.requireNonNull(origin.getResource());
		ItWithStat originItWithStat =
				Objects.requireNonNull(originMemberStat.getItWithCountStats(1L).get());
		originItWithStat.increaseWithCount();
		MemberStatDoc updated = origin;
		// @PreUpdate는 엔티티 변경이 아닌 실제 DB에 있는 데이터가 변경되는 경우 호출된디.
		MemberStatDoc result = repository.saveAndFlush(updated);

		// then
		assertEquals(updated, result);
		assertNotEquals(source, result);
		assertThat(source.getResource().getItWithCountStats(1L).get().getWithCount())
				.isNotEqualTo(result.getResource().getItWithCountStats(1L).get().getWithCount());
		assertThat(source.getResource().getItWithCountStats(1L).get().getWithCount() + 1)
				.isEqualTo(result.getResource().getItWithCountStats(1L).get().getWithCount());
	}
}
