package com.hiit.api.domain.dao.with;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.dao.support.PageData;
import com.hiit.api.domain.dao.support.PageableInfo;
import com.hiit.api.domain.dao.support.Period;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.repository.dao.bussiness.WithRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WithDaoImpl extends AbstractJpaDao<WithEntity, Long, WithData> implements WithDao {

	private final WithRepository repository;

	public WithDaoImpl(
			JpaRepository<WithEntity, Long> jpaRepository,
			AbstractDataConverter<WithEntity, WithData> converter,
			WithRepository repository) {
		super(jpaRepository, converter);
		this.repository = repository;
	}

	@Override
	public Long countByInIt(Long inIt) {
		InItEntity inItEntity = InItEntity.builder().id(inIt).build();
		return repository.countByInIt(inItEntity);
	}

	@Override
	public PageData<WithData> findAllByInIt(Long init, PageableInfo pageable, Long member) {
		InItEntity inItEntity = InItEntity.builder().id(init).build();
		HiitMemberEntity hiitMemberEntity = HiitMemberEntity.builder().id(member).build();
		Page<WithEntity> source = repository.findAllByInIt(inItEntity, pageable, hiitMemberEntity);
		List<WithData> data =
				source.getContent().stream().map(converter::from).collect(Collectors.toList());
		return new PageData<>(pageable, source.getTotalPages(), source.getTotalElements(), data);
	}

	@Override
	public WithData findByInItEntityAndMemberAndPeriod(Long inIt, Long memberId, Period period) {
		LocalDateTime start = period.getStart();
		LocalDateTime end = period.getEnd();
		InItEntity inItEntity = InItEntity.builder().id(inIt).build();
		HiitMemberEntity hiitMemberEntity = HiitMemberEntity.builder().id(memberId).build();
		Optional<WithEntity> source =
				repository.findByInItEntityAndHiitMemberAndCreateAtBetween(
						inItEntity, hiitMemberEntity, start, end);
		if (source.isEmpty()) {
			throw new DataNotFoundException("InIt id : " + inIt + " Member id : " + memberId);
		}
		return converter.from(source.get());
	}
}