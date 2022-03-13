package com.webExample.demo.controller;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks() {
        //given
        var initial = repo.findAll().size();
        repo.save(new Task(LocalDateTime.now(), "foo"));
        repo.save(new Task(LocalDateTime.now(), "doo"));

        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        //then
        assertThat(result).hasSize(initial + 2);
    }

    @Test
    void httpPost_saveTask() {
        Task result = saveTask();
        //then
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("set");
    }

    @Test
    void httpPut_updateTask() {
        //given
        Task savedTask = saveTask();
        //and
        HttpEntity<Task> taskHttpEntity = new HttpEntity<>(new Task(LocalDateTime.now(), "put"));
        //when
        restTemplate.put("http://localhost:" + port + "/tasks/" + savedTask.getId(), taskHttpEntity);
        var result = restTemplate.getForObject("http://localhost:" + port + "/tasks/" + savedTask.getId(), Task.class);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("put");
    }

    private Task saveTask() {
        //given
        HttpEntity<Task> taskHttpEntity = new HttpEntity<>(new Task(LocalDateTime.now(), "set"));
        //when
        return restTemplate.postForObject("http://localhost:" + port + "/tasks", taskHttpEntity, Task.class);
    }
}