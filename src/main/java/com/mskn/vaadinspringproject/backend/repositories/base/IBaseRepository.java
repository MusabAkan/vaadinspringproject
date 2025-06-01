package com.mskn.vaadinspringproject.backend.repositories.base;

import com.mskn.vaadinspringproject.backend.entities.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository için kullanılan temel Generic sınıf
 * @param <T>
 */
@Repository
public interface IBaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}