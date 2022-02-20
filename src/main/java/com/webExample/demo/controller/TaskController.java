package com.webExample.demo.controller;

import com.webExample.demo.model.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webExample.demo.model.Task;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
 class TaskController {
    public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<Task>> readAllTasks () {
        logger.warn("Exposing all the tasks!");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> readAllTasks(Pageable page) {
        logger.info("Custom pageable!");
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<?> readTask(@PathVariable int id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tasks")
    ResponseEntity<?> insertTask (@RequestBody @Valid Task toSave) {
        if (!(toSave.getId() == 0)) return ResponseEntity.badRequest().build();
        Task result = repository.save(toSave);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task toUpdate) {
        if (!repository.existsById(id)) {
          return ResponseEntity.notFound().build();
        }
        toUpdate.setId(id);
        repository.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id) {
        if (!repository.existsById(id)) {
          return ResponseEntity.notFound().build();
        }
        repository.findById(id).ifPresent(task -> task.setDone(!task.isDone()));
        return ResponseEntity.noContent().build();
    }

}
