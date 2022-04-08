package com.webExample.demo.logic;

import com.webExample.demo.TaskConfigurationProperties;
import com.webExample.demo.model.*;
import com.webExample.demo.model.projection.GroupReadModel;
import com.webExample.demo.model.projection.GroupTaskWriteModel;
import com.webExample.demo.model.projection.GroupWriteModel;
import com.webExample.demo.model.projection.ProjectWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("projectServiceTest")
public class ProjectService {
    private final ProjectRepository repository;
    private final TaskGroupRepository groupRepository;
    private final TaskGroupService service;
    private final TaskConfigurationProperties config;

    ProjectService(
            ProjectRepository repository,
            TaskGroupRepository groupRepository,
            @Qualifier("taskGroupService") TaskGroupService service,
            TaskConfigurationProperties config) {
        this.repository = repository;
        this.groupRepository = groupRepository;
        this.service = service;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project createProject(ProjectWriteModel source) {
        return repository.save(source.toProject());
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if(!config.getTemplate().isAllowMultipleTasks() &&
        groupRepository.existsByDoneIsFalseAndProject_Id(projectId))
            throw new IllegalStateException("Only one undone group from project is allowed");

        GroupReadModel result = repository.findById(projectId)
                .map(project -> {
                 var targetGroup = new GroupWriteModel();
                 targetGroup.setDescription(project.getDescription());
                 targetGroup.setTasks(
                         project.getSteps().stream()
                                 .map(projectStep -> {
                                     var task = new GroupTaskWriteModel();
                                     task.setDescription(projectStep.getDescription());
                                     task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                     return task;
                                 }).collect(Collectors.toList())
                 );
                 return service.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return result;
    }
}
