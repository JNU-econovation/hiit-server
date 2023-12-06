package com.hiit.api.domain.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

/**
 * JpaRepository가 기본적으로 제공하는 메서드를 선언한 인터페이스
 *
 * @param <D> 반환할 데이터 타입
 * @param <ID> 데이터의 ID 타입
 */
public interface JpaDao<D, ID> {

	// Jpa
	List<D> findAll();

	List<D> findAll(Sort sort);

	List<D> findAllById(Iterable<ID> longs);

	<S extends D> List<S> saveAll(Iterable<S> entities);

	void flush();

	<S extends D> S saveAndFlush(S entity);

	<S extends D> List<S> saveAllAndFlush(Iterable<S> entities);

	void deleteAllInBatch(Iterable<D> entities);

	void deleteAllByIdInBatch(Iterable<ID> longs);

	void deleteAllInBatch();

	D getReferenceById(ID aLong);

	<S extends D> List<S> findAll(Example<S> example);

	<S extends D> List<S> findAll(Example<S> example, Sort sort);

	Page<D> findAll(Pageable pageable);

	<S extends D> S save(S entity);

	Optional<D> findById(ID aLong);

	boolean existsById(ID aLong);

	long count();

	void deleteById(ID aLong);

	void delete(D entity);

	void deleteAllById(Iterable<? extends ID> longs);

	void deleteAll(Iterable<? extends D> entities);

	void deleteAll();

	<S extends D> Optional<S> findOne(Example<S> example);

	<S extends D> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends D> long count(Example<S> example);

	<S extends D> boolean exists(Example<S> example);

	<S extends D, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);
}
