package com.hiit.api.domain.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

public interface JpaDao<E, ID> {

	// Jpa
	List<E> findAll();

	List<E> findAll(Sort sort);

	List<E> findAllById(Iterable<ID> longs);

	<S extends E> List<S> saveAll(Iterable<S> entities);

	void flush();

	<S extends E> S saveAndFlush(S entity);

	<S extends E> List<S> saveAllAndFlush(Iterable<S> entities);

	void deleteAllInBatch(Iterable<E> entities);

	void deleteAllByIdInBatch(Iterable<ID> longs);

	void deleteAllInBatch();

	E getReferenceById(ID aLong);

	<S extends E> List<S> findAll(Example<S> example);

	<S extends E> List<S> findAll(Example<S> example, Sort sort);

	Page<E> findAll(Pageable pageable);

	<S extends E> S save(S entity);

	Optional<E> findById(ID aLong);

	boolean existsById(ID aLong);

	long count();

	void deleteById(ID aLong);

	void delete(E entity);

	void deleteAllById(Iterable<? extends ID> longs);

	void deleteAll(Iterable<? extends E> entities);

	void deleteAll();

	<S extends E> Optional<S> findOne(Example<S> example);

	<S extends E> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends E> long count(Example<S> example);

	<S extends E> boolean exists(Example<S> example);

	<S extends E, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);
}
