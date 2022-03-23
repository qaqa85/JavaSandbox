package com.webExample.demo.adapter;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM tasks where id=:id") //?1 means arg. number one (id=?1)
    boolean existsById(@Param("id") Integer id);
    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
}
