package com.webExample.demo.model.projection;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class GroupReadModel {
    private String description;
    /**
    * Deadline from the latest task in group
    */
    private LocalDateTime deadline;
    private Set<GroupTaskReadModel> tasks;

    public GroupReadModel(TaskGroup source) {
        description = source.getDescription();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .max(LocalDateTime::compareTo)
                .ifPresent(dateTime -> deadline = dateTime);
        tasks = source.getTasks().stream()
                .map(GroupTaskReadModel::new)
                .collect(Collectors.toSet());
    }
}
