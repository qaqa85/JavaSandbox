package com.webExample.demo;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class TestConfiguration {
    //@Primary //bean priority
    //@ConditionalOnMissingBean <- if other taskRepository bean doesn't exist

    @Bean
    @Primary
    @Profile("!integration") //if profile integration add this bean
    DataSource e2eTestDataSource() {
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
    @Profile("integration") //if profile integration add this bean
    TaskRepository taskRepo() {
        return new TaskRepository(){
            private final Map<Integer, Task> tasks = new HashMap<>();

            @Override
            public List<Task> findAll() {
                return new ArrayList<>(tasks.values());
            }

            @Override
            public Page<Task> findAll(Pageable page) {
                return null;
            }

            @Override
            public Optional<Task> findById(Integer id) {
                return Optional.ofNullable(tasks.get(id));
            }

            @Override
            public List<Task> findAllByGroup_Id(Integer groupId) {
                return null;
            }

            @Override
            public boolean existsById(Integer id) {
                return tasks.containsKey(id);
            }

            @Override
            public List<Task> findByDone(boolean done) {
                return null;
            }

            @Override
            public Task save(Task entity) {
                int key = tasks.size() + 1;
                entity.setId(key);
                tasks.put(key, entity);
                return tasks.get(key);
            }

            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {
                return false;
            }

            @Override
            public List<Task> findAllByDoneIsFalseAndDeadlineIsLessThanEqualOrDoneIsFalseAndDeadlineIsNull(LocalDateTime dateTime) {
                return null;
            }
        };
    }
}
