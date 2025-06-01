package com.mskn.vaadinspringproject.backend.services.base;

import com.mskn.vaadinspringproject.backend.entities.base.BaseEntity;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service için kullanılan temel Generic abastract sınıf
 *
 * @param <T>
 */
@Service
@Transactional(rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T extends BaseEntity> implements IBaseService<T> {
    protected abstract IBaseRepository<T> getRepository();

    @Override
    public T save(T t) {
        return getRepository().save(t);
    }

    @Override
    public Boolean existsById(Long id) {
        return getRepository().existsById(id);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public void delete(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void deleteAll(List<T> entities) {
        getRepository().deleteAll(entities);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    @Override
    public List<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable).getContent();
    }

    @Override
    public List<T> findAll(Example<T> example, Pageable pageable) {
        return getRepository().findAll(example, pageable).getContent();
    }

    @Override
    public long countAll(Example<T> example){
        return  getRepository().count(example);
    }
    @Override
    public List<T> findAllById(List<Long> ids) {
        return getRepository().findAllById(ids);
    }

    @Override
    public long countAll() {
        return getRepository().count();
    }
}