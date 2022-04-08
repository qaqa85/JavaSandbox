package com.webExample.demo.model.projection;

import com.webExample.demo.model.Project;
import com.webExample.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class GroupWriteModel {
    @NotBlank(message = "Task group's description must not be empty")
    private String description;
    private LocalDateTime deadline;
    @Valid
    private List<GroupTaskWriteModel> tasks = new ArrayList<>();

    public GroupWriteModel() {
        tasks.add(new GroupTaskWriteModel());
    }

    public TaskGroup toGroup(Project project) {
        var result = new TaskGroup();
        result.setDeadline(deadline);
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(result))
                        .collect(Collectors.toSet())
        );
        result.setProject(project);
        return result;
    }
}
