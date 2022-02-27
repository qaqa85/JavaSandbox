package com.webExample.demo.adapter;

import com.webExample.demo.model.Project;
import com.webExample.demo.model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("from Project e join fetch e.steps")
    List<Project> findAll ();
}
