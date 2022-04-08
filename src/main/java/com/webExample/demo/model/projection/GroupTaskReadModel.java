package com.webExample.demo.model.projection;

import com.webExample.demo.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class GroupTaskReadModel {
    private String description;
    private boolean done;
    private LocalDateTime deadline;


    public GroupTaskReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
        deadline = source.getDeadline();
    }
}
