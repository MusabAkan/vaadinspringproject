package com.mskn.vaadinspringproject.backend.repositories;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Task;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;

import java.time.LocalDate;
import java.util.List;

public interface ITaskRepository extends IBaseRepository<Task> {
    List<Task> findAllByAssignedUser(AppUser user);
    List<Task> findByAssignedUserAndDueDateBetween(AppUser user, LocalDate start, LocalDate end);
}