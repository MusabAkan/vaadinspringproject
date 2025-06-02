package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.Customer;
import com.mskn.vaadinspringproject.backend.repositories.ICustomerRepository;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;
import com.mskn.vaadinspringproject.backend.services.base.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements ICustomerService {

    private final ICustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    protected IBaseRepository<Customer> getRepository() {
        return customerRepository;
    }

    @Override
    public List<Customer> findByCreatedAtAfter(LocalDateTime dateTime) {
        return customerRepository.findByCreatedAtAfter(dateTime);
    }
}