package com.webExample.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TaskController.class)
class TaskControllerLightIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        //given
        when(repo.findById(anyInt()))
                .thenReturn(Optional.of(new Task(LocalDateTime.now(),"foo")));


        //when + then
        mockMvc.perform(get("/tasks/123"))
                .andDo(print())
                .andExpect(content().string(containsString("\"description\":\"foo\"")));
    }

    @Test
    void httpPutOne_returnsOneTask() throws Exception {
        //Java 8 date/time type `java.time.LocalDateTime` not supported by default:
        // add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                        .build();
        //given
        when(repo.existsById(anyInt())).thenReturn(true);
        //and
        when(repo.findById(anyInt())).thenReturn(Optional.of(new Task(LocalDateTime.now(), "foo")));
        //when
         mockMvc.perform(put("/tasks/123").content(objectMapper.writeValueAsString(new Task(LocalDateTime.now(), "foo")))
                         .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}