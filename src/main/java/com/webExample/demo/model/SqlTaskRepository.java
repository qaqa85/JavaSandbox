package com.webExample.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM tasks where id=:id") //?1 means arg. number one (id=?1)
    boolean existsById(@Param("id") Integer id);
}
