package com.mskn.vaadinspringproject.backend.repositories;

import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ICustomerRepository extends IBaseRepository<Customer> {
    List<Customer> findByCreatedAtAfter(LocalDateTime dateTime);
}