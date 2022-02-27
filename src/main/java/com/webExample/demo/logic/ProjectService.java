package com.webExample.demo.logic;

import com.webExample.demo.TaskConfigurationProperties;
import com.webExample.demo.model.*;
import com.webExample.demo.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final TaskGroupRepository groupRepository;
    private final TaskConfigurationProperties config;

    ProjectService(ProjectRepository repository, TaskGroupRepository groupRepository, TaskConfigurationProperties config) {
        this.repository = repository;
        this.groupRepository = groupRepository;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project createProject(Project source) {
        return repository.save(source);
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if(!config.getTemplate().isAllowMultipleTasks() &&
        groupRepository.existsByDoneIsFalseAndProject_Id(projectId))
            throw new IllegalStateException("Only one undone group from project is allowed");

        TaskGroup result = repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> new Task(
                                            deadline.plusDays(projectStep.getDaysToDeadline()),
                                            projectStep.getDescription())
                                    ).collect(Collectors.toSet())
                    );
                    return targetGroup;
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }
}
