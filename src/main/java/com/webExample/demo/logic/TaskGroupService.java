package com.webExample.demo.logic;

import com.webExample.demo.model.TaskGroup;
import com.webExample.demo.model.TaskGroupRepository;
import com.webExample.demo.model.TaskRepository;
import com.webExample.demo.model.projection.GroupReadModel;
import com.webExample.demo.model.projection.GroupWriteModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;

@Service("taskGroupServiceTest")
public class TaskGroupService {
    private final TaskGroupRepository repository;
    private final TaskRepository taskRepository;

    TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the task first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Task with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
