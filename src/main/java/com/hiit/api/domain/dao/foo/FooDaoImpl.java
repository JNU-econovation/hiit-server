package com.hiit.api.domain.dao.foo;

import static java.util.stream.Collectors.toList;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.entity.business.FooEntity;
import com.hiit.api.repository.entity.business.param.FooEntitySearchParam;
import com.hiit.api.repository.repository.bussiness.custom.FooCustomRepository;
import com.hiit.api.repository.repository.bussiness.jpa.FooJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class FooDaoImpl extends AbstractJpaDao<FooEntity, Long, FooData> implements FooDao {

	private final FooJpaRepository repository;
	private final FooCustomRepository customRepository;

	public FooDaoImpl(
			JpaRepository<FooEntity, Long> jpaRepository,
			AbstractDataConverter<FooEntity, FooData> converter,
			FooCustomRepository customRepository) {
		super(jpaRepository, converter);
		this.repository = (FooJpaRepository) jpaRepository;
		this.customRepository = customRepository;
	}

	@Override
	public Optional<FooData> findTopByNameAndDeletedFalseOrderByIdDesc(String name) {
		return repository.findTopByNameAndDeletedFalseOrderByIdDesc(name).map(converter::from);
	}

	@Override
	public List<FooData> findAllByNameAndDeletedFalseOrderByIdDesc(String name) {
		return repository.findAllByNameAndDeletedFalseOrderByIdDesc(name).stream()
				.map(converter::from)
				.collect(toList());
	}

	@Override
	public Page<FooData> findAllByNameAndDeletedFalse(Pageable pageable, String name) {
		return customRepository.findAllByNameAndDeletedFalse(pageable, name).map(converter::from);
	}

	@Override
	public Page<FooData> search(Pageable pageable, FooDaoSearchParam param) {
		FooEntitySearchParam searchParam =
				FooEntitySearchParam.builder()
						.id(param.getId().orElse(null))
						.name(param.getName().orElse(null))
						.build();
		return customRepository.search(pageable, searchParam).map(converter::from);
	}
}
