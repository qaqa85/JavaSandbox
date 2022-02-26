package com.webExample.demo.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TASK_GROUPS")
@Setter @NoArgsConstructor
public class TaskGroup extends BaseTask{
    //FetchType.Lazy - fetch immediately
    //FetchType.EAGER - fetch when getTasks is called
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;

    @Embedded
    @AttributeOverrides ({
            @AttributeOverride(column = @Column(name = "UPDATED_ON"), name = "updatedOn"),
            @AttributeOverride(column = @Column(name = "CREATED_ON"), name = "createdOn")})
    private Audit audit = new Audit();

    public Set<Task> getTasks() {
        return tasks;
    }

    Audit getAudit() {
        return audit;
    }
}
