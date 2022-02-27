package com.webExample.demo.adapter;

import com.webExample.demo.model.TaskGroup;
import com.webExample.demo.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {
    @Override
    @Query("select distinct g from TaskGroup g inner join fetch g.tasks")
    List<TaskGroup> findAll();
    @Override
    boolean existsByDoneIsFalseAndProject_Id(int projectId);
}
