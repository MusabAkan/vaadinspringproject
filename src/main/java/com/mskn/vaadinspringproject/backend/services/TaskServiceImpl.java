package com.mskn.vaadinspringproject.backend.services;

import com.mskn.vaadinspringproject.backend.entities.Task;
import com.mskn.vaadinspringproject.backend.repositories.ITaskRepository;
import com.mskn.vaadinspringproject.backend.repositories.base.IBaseRepository;
import com.mskn.vaadinspringproject.backend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task> implements ITaskService {

    private final com.mskn.vaadinspringproject.backend.repositories.ITaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    protected IBaseRepository<Task> getRepository() {
        return taskRepository;
    }
}