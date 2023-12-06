package com.hiit.api.domain.dao.foo;

import com.hiit.api.domain.dao.JpaDao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FooDao extends JpaDao<FooData, Long> {

	Optional<FooData> findTopByNameAndDeletedFalseOrderByIdDesc(String name);

	List<FooData> findAllByNameAndDeletedFalseOrderByIdDesc(String name);

	Page<FooData> findAllByNameAndDeletedFalse(Pageable pageable, String name);

	Page<FooData> search(Pageable pageable, FooDaoSearchParam param);
}
