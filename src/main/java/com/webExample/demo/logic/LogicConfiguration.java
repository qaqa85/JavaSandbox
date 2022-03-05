package com.webExample.demo.logic;

import com.webExample.demo.TaskConfigurationProperties;
import com.webExample.demo.model.ProjectRepository;
import com.webExample.demo.model.TaskGroupRepository;
import com.webExample.demo.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ImportResource("classpath:applicationContext.xml")
class LogicConfiguration {
   @Bean
    ProjectService projectService(
            ProjectRepository repository,
            TaskGroupRepository taskGroupRepository,
            TaskConfigurationProperties config
    ) {
        return new ProjectService(repository, taskGroupRepository, config);
    }

    @Bean
    TaskGroupService  taskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        return new TaskGroupService(repository, taskRepository);
    }
}