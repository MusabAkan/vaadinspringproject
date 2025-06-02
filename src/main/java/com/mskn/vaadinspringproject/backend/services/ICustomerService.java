package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.services.base.IBaseService;

import java.time.LocalDateTime;
import java.util.List;


public interface ICustomerService extends IBaseService<Customer> {
    List<Customer> findByCreatedAtAfter(LocalDateTime dateTime);
}