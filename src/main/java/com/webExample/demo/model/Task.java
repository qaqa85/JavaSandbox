package com.webExample.demo.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
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

    Task(LocalDateTime deadline, TaskGroup group) {
        this.deadline = deadline;
        this.group = group;
    }

    public TaskGroup getGroup() {
        return group;
    }

    private Audit getAudit() {
        return audit;
    }
}


