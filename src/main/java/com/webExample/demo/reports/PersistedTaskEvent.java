package com.webExample.demo.reports;

import com.webExample.demo.model.event.TaskEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "tasks_events")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
class PersistedTaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalDateTime occurrence;
    int taskId;
    String name;

    PersistedTaskEvent(TaskEvent source) {
        taskId = source.getTaskId();
        name = source.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }

    LocalDateTime getOccurrence() {
        return occurrence;
    }
}
