package com.webExample.demo.logic;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskRepository;
import com.webExample.demo.model.projection.GroupReadModel;
import com.webExample.demo.model.projection.GroupTaskReadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<List<GroupTaskReadModel>> findAllAsync() {
        var result = repository.findAll().stream().map(GroupTaskReadModel::new).collect(Collectors.toList());
        return CompletableFuture.supplyAsync(() -> result);
    }

    public List<GroupTaskReadModel> getAllTasksForToday() {
        LocalDateTime highestTodayDate = LocalDateTime.now().withHour(23).withMinute(59);
        return repository.findAllByDoneIsFalseAndDeadlineIsLessThanEqualOrDoneIsFalseAndDeadlineIsNull(highestTodayDate).stream().map(GroupTaskReadModel::new).collect(Collectors.toList());
    }
}
