package com.webExample.demo.reports;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
class ReportController {
    private final TaskRepository taskRepository;
    private final PersistedTaskEventRepository eventRepository;

    ReportController(TaskRepository taskRepository, PersistedTaskEventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithChangesCount(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/done_before/{id}")
    ResponseEntity<TaskWithDeadlineCheck> readTaskWithDeadlineCheck(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithDeadlineCheck(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static class TaskWithDeadlineCheck {
        public String description;
        public boolean doneBeforeDeadline;

        TaskWithDeadlineCheck(Task task, List<PersistedTaskEvent> events) {
            this.description = task.getDescription();

            if (task.isDone() && task.getDeadline() != null)
            {
                var result = events.stream()
                        .map(PersistedTaskEvent::getOccurrence)
                        .max(LocalDateTime::compareTo)
                        .orElseThrow(() -> new NullPointerException("No data in task_events"));

                this.doneBeforeDeadline = result.isBefore(task.getDeadline());
            }
            else this.doneBeforeDeadline = task.isDone();
        }
    }

    private static class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;

        TaskWithChangesCount(Task task, List<PersistedTaskEvent> events) {
            description = task.getDescription();
            done = task.isDone();
            changesCount = events.size();
        }
    }
}