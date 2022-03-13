package com.webExample.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
abstract class BaseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected LocalDateTime deadline;

    @NotBlank(message = "Task's description must not be null")
    protected String description;
    protected boolean done;

    public void updateFrom(final BaseTask source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
    }
}
