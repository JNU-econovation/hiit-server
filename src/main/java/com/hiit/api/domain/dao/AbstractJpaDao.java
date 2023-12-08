package com.hiit.api.domain.dao;

import static java.util.stream.Collectors.toList;

import com.hiit.api.common.marker.dto.AbstractData;
import com.hiit.api.common.marker.entity.EntityMarker;
import java.util.ArrayList;
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
 * @param <D> 반환할 데이터 타입
 */
@RequiredArgsConstructor
public abstract class AbstractJpaDao<E extends EntityMarker, ID, D extends AbstractData>
		implements JpaDao<D, ID> {

	protected final JpaRepository<E, ID> jpaRepository;
	protected final AbstractDataConverter<E, D> converter;

	@Override
	public List<D> findAll() {
		return jpaRepository.findAll().stream().map(converter::from).collect(toList());
	}

	@Override
	public List<D> findAll(Sort sort) {
		return jpaRepository.findAll(sort).stream().map(converter::from).collect(toList());
	}

	@Override
	public List<D> findAllById(Iterable<ID> longs) {
		return jpaRepository.findAllById(longs).stream().map(converter::from).collect(toList());
	}

	@Override
	public <S extends D> List<S> saveAll(Iterable<S> entities) {
		List<E> sources = new ArrayList<>();
		for (D entity : entities) {
			sources.add(converter.to(entity));
		}
		return (List<S>) jpaRepository.saveAll(sources).stream().map(converter::from).collect(toList());
	}

	@Override
	public void flush() {
		jpaRepository.flush();
	}

	@Override
	public <S extends D> S saveAndFlush(S entity) {
		E source = converter.to(entity);
		return (S) converter.from(jpaRepository.saveAndFlush(source));
	}

	@Override
	public <S extends D> List<S> saveAllAndFlush(Iterable<S> entities) {
		List<E> sources = new ArrayList<>();
		for (D entity : entities) {
			sources.add(converter.to(entity));
		}
		return (List<S>)
				jpaRepository.saveAllAndFlush(sources).stream().map(converter::from).collect(toList());
	}

	@Override
	public void deleteAllInBatch(Iterable<D> entities) {
		List<E> sources = new ArrayList<>();
		for (D entity : entities) {
			sources.add(converter.to(entity));
		}
		jpaRepository.deleteAllInBatch(sources);
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
	public D getReferenceById(ID aLong) {
		E source = jpaRepository.getReferenceById(aLong);
		return converter.from(source);
	}

	@Override
	public <S extends D> List<S> findAll(Example<S> example) {
		D data = example.getProbe();
		Example<E> source = Example.of(converter.to(data), example.getMatcher());
		return (List<S>) jpaRepository.findAll(source).stream().map(converter::from).collect(toList());
	}

	@Override
	public <S extends D> List<S> findAll(Example<S> example, Sort sort) {
		D data = example.getProbe();
		Example<E> source = Example.of(converter.to(data), example.getMatcher());
		return (List<S>)
				jpaRepository.findAll(source, sort).stream().map(converter::from).collect(toList());
	}

	@Override
	public Page<D> findAll(Pageable pageable) {
		return jpaRepository.findAll(pageable).map(converter::from);
	}

	@Override
	public <S extends D> S save(S entity) {
		E source = converter.to(entity);
		return (S) converter.from(jpaRepository.save(source));
	}

	@Override
	public Optional<D> findById(ID aLong) {
		return jpaRepository.findById(aLong).map(converter::from);
	}

	@Override
	public boolean existsById(ID aLong) {
		return jpaRepository.existsById(aLong);
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
	public void delete(D entity) {
		E source = converter.to(entity);
		jpaRepository.delete(source);
	}

	@Override
	public void deleteAllById(Iterable<? extends ID> longs) {
		jpaRepository.deleteAllById(longs);
	}

	@Override
	public void deleteAll(Iterable<? extends D> entities) {
		List<E> sources = new ArrayList<>();
		for (D entity : entities) {
			sources.add(converter.to(entity));
		}
		jpaRepository.deleteAll(sources);
	}

	@Override
	public void deleteAll() {
		jpaRepository.deleteAll();
	}

	@Override
	public <S extends D> Optional<S> findOne(Example<S> example) {
		D data = example.getProbe();
		Example<E> source = Example.of(converter.to(data), example.getMatcher());
		return (Optional<S>) jpaRepository.findOne(source).map(converter::from);
	}

	@Override
	public <S extends D> Page<S> findAll(Example<S> example, Pageable pageable) {
		D data = example.getProbe();
		Example<E> source = Example.of(converter.to(data), example.getMatcher());
		return (Page<S>) jpaRepository.findAll(source, pageable).map(converter::from);
	}

	@Override
	public <S extends D> long count(Example<S> example) {
		D data = example.getProbe();
		Example<E> source = Example.of(converter.to(data), example.getMatcher());
		return jpaRepository.count(source);
	}

	@Override
	public <S extends D> boolean exists(Example<S> example) {
		D data = example.getProbe();
		Example<E> source = Example.of(converter.to(data), example.getMatcher());
		return jpaRepository.exists(source);
	}

	@Override
	public <S extends D, R> R findBy(
			Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		throw new UnsupportedOperationException();
	}
}
