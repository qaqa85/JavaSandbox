package com.webExample.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter @Getter
@Table(name = "tasks")
public class Task extends BaseTask {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

    @Embedded
    @AttributeOverrides ({
            @AttributeOverride(column = @Column(name = "UPDATED_ON"), name = "updatedOn"),
            @AttributeOverride(column = @Column(name = "CREATED_ON"), name = "createdOn")})
    private Audit audit = new Audit();

    public Task(LocalDateTime deadline, String description, TaskGroup group) {
        this.deadline = deadline;
        this.description = description;
        if(group != null) {
            this.group = group;
        }
    }

    public Task(LocalDateTime deadline, String description) {
        this(deadline, description, null);
    }
}


