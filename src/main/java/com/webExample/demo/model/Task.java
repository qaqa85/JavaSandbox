package com.webExample.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Task's description must not be null")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn"),
    })
    private Audit audit = new Audit();

    public Task(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public  void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
    }
}


