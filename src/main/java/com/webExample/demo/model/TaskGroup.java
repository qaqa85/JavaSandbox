package com.webExample.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TASK_GROUPS")
@Setter @Getter
@NoArgsConstructor
public class TaskGroup extends BaseTask{
    //FetchType.Lazy - fetch immediately
    //FetchType.EAGER - fetch when getTasks is called
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Embedded
    @AttributeOverrides ({
            @AttributeOverride(column = @Column(name = "UPDATED_ON"), name = "updatedOn"),
            @AttributeOverride(column = @Column(name = "CREATED_ON"), name = "createdOn")})
    private Audit audit = new Audit();

    public TaskGroup(String description, Set<Task> tasks) {
        this.tasks = tasks;
        this.description = description;
    }
}
