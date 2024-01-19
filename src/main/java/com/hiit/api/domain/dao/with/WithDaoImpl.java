package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.dto.PageRequest;
import com.hiit.api.domain.support.entity.PageElements;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.repository.dao.bussiness.WithRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.entity.business.with.WithStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
	public boolean existsByIdAndStatus(Long withId, WithStatus status) {
		return repository.existsByIdAndStatus(withId, status);
	}

	@Override
	public Long countEndByInIt(Long inItId) {
		InItEntity inItEntity = InItEntity.builder().id(inItId).build();
		return repository.countByInItAndStatus(inItEntity, WithStatus.END);
	}

	@Override
	public PageElements<WithEntity> findAllByInItAndMemberAndStatus(
			Long initId, PageRequest pageable, Long memberId, Period period, WithStatus status) {
		InItEntity inItEntity = InItEntity.builder().id(initId).build();
		HiitMemberEntity hiitMemberEntity = HiitMemberEntity.builder().id(memberId).build();
		Page<WithEntity> source =
				repository.findAllByInItAndStatus(
						inItEntity, pageable, hiitMemberEntity, period.getStart(), period.getEnd(), status);
		List<WithEntity> data = source.getContent();
		return new PageElements<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}

	@Override
	public PageElements<WithEntity> findAllByInItAndStatus(
			Long initId, PageRequest pageable, Period period, WithStatus status) {
		InItEntity inItEntity = InItEntity.builder().id(initId).build();
		Page<WithEntity> source =
				repository.findAllByInItAndStatus(
						inItEntity, pageable, null, period.getStart(), period.getEnd(), status);
		List<WithEntity> data = source.getContent();
		return new PageElements<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}

	@Override
	public PageElements<WithEntity> findAllByInItAndMemberIdAndStatus(
			Long initId, Long memberId, PageRequest pageable, WithStatus status) {
		InItEntity inItEntity = InItEntity.builder().id(initId).build();
		Page<WithEntity> source =
				repository.findAllByInItAndMemberIdAndStatus(inItEntity, memberId, pageable, status);
		List<WithEntity> data = source.getContent();
		return new PageElements<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}

	@Override
	public List<WithEntity> findAllByInItAndMemberAndStatus(
			Long initId, Long memberId, WithStatus status) {
		InItEntity inItEntity = InItEntity.builder().id(initId).build();
		HiitMemberEntity hiitMemberEntity = HiitMemberEntity.builder().id(memberId).build();
		return repository.findAllByInItAndStatus(inItEntity, hiitMemberEntity);
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
		source =
				source.stream()
						.filter(s -> !s.getStatus().equals(WithStatus.END))
						.collect(Collectors.toList());
		if (source.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(source.get(0));
	}

	@Override
	public PageElements<WithEntity> findAllByInItRandomAndStatus(
			Long initId, Integer size, WithStatus status) {
		Page<WithEntity> source = repository.findAllByInItRandomAndStatus(initId, size, status);
		List<WithEntity> data = source.getContent();
		Pageable pageable = source.getPageable();
		if (pageable.isUnpaged()) {
			return new PageElements<>(0, 0, source.getTotalPages(), source.getTotalElements(), data);
		}
		return new PageElements<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}

	@Override
	public List<WithEntity> findAllByInItAndMemberAndStatusEnd(Long initId, Long memberId) {
		return repository.findAllByInItAnsStatus(
				InItEntity.builder().id(initId).build(),
				HiitMemberEntity.builder().id(memberId).build(),
				WithStatus.END);
	}
}
