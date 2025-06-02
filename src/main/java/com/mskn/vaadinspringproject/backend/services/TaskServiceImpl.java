package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.AppUser;
import com.mskn.vaadinspringproject.backend.entities.Task;
import com.mskn.vaadinspringproject.backend.repositories.ITaskRepository;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;
import com.mskn.vaadinspringproject.backend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements ITaskService {

    private final ITaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    protected IBaseRepository<Task> getRepository() {
        return taskRepository;
    }

    @Override
    public List<Task> findByAssignedUser(AppUser user) {
        return taskRepository.findAllByAssignedUser(user);
    }

    @Override
    public List<Task> findByAssignedUserAndDueDateBetween(AppUser user, LocalDate start, LocalDate end) {
        return taskRepository.findByAssignedUserAndDueDateBetween(user, start, end);
    }
}