package com.webExample.demo.model;

import java.util.List;
import java.util.Optional;

public interface TaskGroupRepository {
    List<TaskGroup> findAll();

    Optional<TaskGroup> findById(Integer id);

    TaskGroup save(TaskGroup entity);

    boolean existsByDoneIsFalseAndProject_Id(int projectId);

    boolean existsById(int groupId);

    boolean existsByDescription(String description);
}
