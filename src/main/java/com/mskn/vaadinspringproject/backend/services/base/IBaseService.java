package com.mskn.vaadinspringproject.backend.services.base;

import com.mskn.vaadinspringproject.backend.entities.base.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Service için kullanılan temel interface sınıf
 * @param <T>
 */
public interface IBaseService<T extends BaseEntity> {

    T save(T t);

    Boolean existsById(Long id);

    List<T> saveAll(List<T> entities);

    void delete(T entity);

    void delete(Long id);

    void deleteAll(List<T> entities);

    List<T> findAll();

    Optional<T> findById(Long id);

    List<T> findAll(Sort sort);

    List<T> findAll(Pageable pageable);

    List<T> findAll(Example<T> example, Pageable pageable);

    List<T> findAllById(List<Long> ids);

    long countAll();

    long countAll(Example<T> example);
}
