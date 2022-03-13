package com.webExample.demo.model.projection;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskGroup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

    public Task toTask(TaskGroup group) {
        return new Task(deadline, description, group);
    }
}
