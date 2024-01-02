package com.hiit.api.domain.dao;

import com.hiit.api.common.marker.entity.EntityMarker;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

/**
 * JpaRepository가 기본적으로 제공하는 메서드를 구현한 추상 클래스
 *
 * @param <E> JPA 엔티티 타입
 * @param <ID> ID 타입
 */
@RequiredArgsConstructor
public abstract class AbstractJpaDao<E extends EntityMarker, ID> implements JpaDao<E, ID> {

	protected final JpaRepository<E, ID> jpaRepository;

	@Override
	public List<E> findAll() {
		return jpaRepository.findAll();
	}

	@Override
	public List<E> findAll(Sort sort) {
		return jpaRepository.findAll(sort);
	}

	@Override
	public List<E> findAllById(Iterable<ID> longs) {
		return jpaRepository.findAllById(longs);
	}

	@Override
	public <S extends E> List<S> saveAll(Iterable<S> entities) {
		return jpaRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		jpaRepository.flush();
	}

	@Override
	public <S extends E> S saveAndFlush(S entity) {
		return jpaRepository.saveAndFlush(entity);
	}

	@Override
	public <S extends E> List<S> saveAllAndFlush(Iterable<S> entities) {
		return jpaRepository.saveAllAndFlush(entities);
	}

	@Override
	public void deleteAllInBatch(Iterable<E> entities) {
		jpaRepository.deleteAllInBatch(entities);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<ID> longs) {
		jpaRepository.deleteAllByIdInBatch(longs);
	}

	@Override
	public void deleteAllInBatch() {
		jpaRepository.deleteAllInBatch();
	}

	@Override
	public E getReferenceById(ID aLong) {
		return jpaRepository.getReferenceById(aLong);
	}

	@Override
	public <S extends E> List<S> findAll(Example<S> example) {
		return jpaRepository.findAll(example);
	}

	@Override
	public <S extends E> List<S> findAll(Example<S> example, Sort sort) {
		return jpaRepository.findAll(example, sort);
	}

	@Override
	public Page<E> findAll(Pageable pageable) {
		return jpaRepository.findAll(pageable);
	}

	@Override
	public <S extends E> S save(S entity) {
		return jpaRepository.save(entity);
	}

	@Override
	public Optional<E> findById(ID aLong) {
		return jpaRepository.findById(aLong);
	}

	@Override
	public boolean existsById(ID withId) {
		return jpaRepository.existsById(withId);
	}

	@Override
	public long count() {
		return jpaRepository.count();
	}

	@Override
	public void deleteById(ID aLong) {
		jpaRepository.deleteById(aLong);
	}

	@Override
	public void delete(E entity) {
		jpaRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends ID> longs) {
		jpaRepository.deleteAllById(longs);
	}

	@Override
	public void deleteAll(Iterable<? extends E> entities) {
		jpaRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		jpaRepository.deleteAll();
	}

	@Override
	public <S extends E> Optional<S> findOne(Example<S> example) {
		return jpaRepository.findOne(example);
	}

	@Override
	public <S extends E> Page<S> findAll(Example<S> example, Pageable pageable) {
		return jpaRepository.findAll(example, pageable);
	}

	@Override
	public <S extends E> long count(Example<S> example) {
		return jpaRepository.count(example);
	}

	@Override
	public <S extends E> boolean exists(Example<S> example) {
		return jpaRepository.exists(example);
	}

	@Override
	public <S extends E, R> R findBy(
			Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return jpaRepository.findBy(example, queryFunction);
	}
}
