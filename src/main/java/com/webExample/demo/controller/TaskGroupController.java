package com.webExample.demo.controller;

import com.webExample.demo.logic.TaskGroupService;
import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskRepository;
import com.webExample.demo.model.projection.GroupReadModel;
import com.webExample.demo.model.projection.GroupTaskReadModel;
import com.webExample.demo.model.projection.GroupWriteModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
class TaskGroupController {
    private final TaskGroupService taskGroupService;
    private final TaskRepository taskRepository;

    TaskGroupController(TaskGroupService taskGroupService, TaskRepository taskRepository) {
        this.taskGroupService = taskGroupService;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/groups")
    ResponseEntity<?> createGroup (@RequestBody @Valid GroupWriteModel model) {
        GroupReadModel result = taskGroupService.createGroup(model);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping("/groups")
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(taskGroupService.readAll());
    }

    @PatchMapping("/groups/{id}")
    ResponseEntity<?> toggleGroup(@PathVariable int id) {
        taskGroupService.toggleGroup(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/groups/{id}/tasks")
    ResponseEntity<List<GroupTaskReadModel>> readAllTasksFromGroup (@PathVariable int id) {
        List<Task> result = taskRepository.findAllByGroup_Id(id);
        /*Should be in service*/
        List<GroupTaskReadModel> groupReadModels = taskRepository.findAllByGroup_Id(id).stream()
                .map(GroupTaskReadModel::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupReadModels);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
