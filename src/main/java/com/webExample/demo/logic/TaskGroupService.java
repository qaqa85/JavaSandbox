package com.webExample.demo.logic;

import com.webExample.demo.model.Project;
import com.webExample.demo.model.TaskGroup;
import com.webExample.demo.model.TaskGroupRepository;
import com.webExample.demo.model.TaskRepository;
import com.webExample.demo.model.projection.GroupReadModel;
import com.webExample.demo.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

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
        return createGroup(source, null);
    }

    GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return repository.findAll()
                .stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public GroupReadModel readById(int groupId) {
        return repository.findById(groupId).map(GroupReadModel::new).orElseThrow(() -> new IllegalArgumentException("Task with given id not found"));
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
