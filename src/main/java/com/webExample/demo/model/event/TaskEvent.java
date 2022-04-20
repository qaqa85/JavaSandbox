package com.webExample.demo.model.event;

import com.webExample.demo.model.Task;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;

@Getter(AccessLevel.PUBLIC)

public abstract class TaskEvent {
    public static TaskEvent changed(Task source) {
        return source.isDone() ? new TaskDone(source) : new TaskUndone(source);
    }

    private final int taskId;
    private final Instant occurrence;

    TaskEvent(int taskId, Clock clock) {
        this.taskId = taskId;
        this.occurrence = Instant.now(clock);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "taskId=" + taskId +
                ", occurrence=" + occurrence +
                '}';
    }
}
