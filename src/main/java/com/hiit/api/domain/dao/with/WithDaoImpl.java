package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.repository.dao.bussiness.WithRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WithDaoImpl extends AbstractJpaDao<WithEntity, Long> implements WithDao {

	private final WithRepository repository;

	public WithDaoImpl(JpaRepository<WithEntity, Long> jpaRepository, WithRepository repository) {
		super(jpaRepository);
		this.repository = repository;
	}

	@Override
	public Long countByInIt(Long inItId) {
		InItEntity inItEntity = InItEntity.builder().id(inItId).build();
		return repository.countByInIt(inItEntity);
	}

	@Override
	public PageElements<WithEntity> findAllByInItAndMember(
			Long initId, PageRequest pageable, Long memberId, Period period) {
		InItEntity inItEntity = InItEntity.builder().id(initId).build();
		HiitMemberEntity hiitMemberEntity = HiitMemberEntity.builder().id(memberId).build();
		Page<WithEntity> source =
				repository.findAllByInIt(
						inItEntity, pageable, hiitMemberEntity, period.getStart(), period.getEnd());
		List<WithEntity> data = source.getContent();
		return new PageElements<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}

	@Override
	public PageElements<WithEntity> findAllByInIt(Long initId, PageRequest pageable, Period period) {
		InItEntity inItEntity = InItEntity.builder().id(initId).build();
		Page<WithEntity> source =
				repository.findAllByInIt(inItEntity, pageable, null, period.getStart(), period.getEnd());
		List<WithEntity> data = source.getContent();
		return new PageElements<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}

	@Override
	public List<WithEntity> findAllByInItAndMember(Long initId, Long memberId) {
		InItEntity inItEntity = InItEntity.builder().id(initId).build();
		HiitMemberEntity hiitMemberEntity = HiitMemberEntity.builder().id(memberId).build();
		return repository.findAllByInIt(inItEntity, hiitMemberEntity);
	}

	@Override
	public Optional<WithEntity> findByInItEntityAndMemberAndPeriod(
			Long inItId, Long memberId, Period period) {
		LocalDateTime start = period.getStart();
		LocalDateTime end = period.getEnd();
		InItEntity inItEntity = InItEntity.builder().id(inItId).build();
		HiitMemberEntity hiitMemberEntity = HiitMemberEntity.builder().id(memberId).build();
		List<WithEntity> source =
				repository.findAllByInItEntityAndHiitMemberAndCreateAtBetween(
						inItEntity, hiitMemberEntity, start, end);
		if (source.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(source.get(0));
	}

	@Override
	public PageElements<WithEntity> findAllByInItRandom(Long initId, Integer size) {
		Page<WithEntity> source = repository.findAllByInItRandom(initId, size);
		List<WithEntity> data = source.getContent();
		Pageable pageable = source.getPageable();
		return new PageElements<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}
}
