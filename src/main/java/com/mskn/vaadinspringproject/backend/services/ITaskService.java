package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Task;
import com.mskn.vaadinspringproject.backend.services.base.IBaseService;

import java.time.LocalDate;
import java.util.List;

public interface ITaskService extends IBaseService<Task> {
    List<Task> findByAssignedUser(AppUser user);

    List<Task> findByAssignedUserAndDueDateBetween(AppUser user, LocalDate start, LocalDate end);
}