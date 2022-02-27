package com.webExample.demo.model.projection;

import com.webExample.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class GroupWriteModel {
    private String description;
    private Set<GroupTaskWriteModel> tasks;

    public TaskGroup toGroup() {
        var result = tasks.stream()
                .map(GroupTaskWriteModel::toTask)
                .collect(Collectors.toSet());
        return new TaskGroup(description, result);
    }
}
