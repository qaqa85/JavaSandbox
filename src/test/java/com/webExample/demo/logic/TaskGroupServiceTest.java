package com.webExample.demo.logic;

import com.webExample.demo.model.TaskGroup;
import com.webExample.demo.model.TaskGroupRepository;
import com.webExample.demo.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw IllegalStateException")
    void toggleGroup_undoneTasksExists_throwsIllegalStateException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        //system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(0));
        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Group has undone tasks. Done all the task first");
    }

    @Test
    @DisplayName("should throw IllegalArgumentException")
    void toggleGroup_noUndoneTasksExists_And_noTaskGroup_throwsIllegalArgumentException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //when
        var exception = catchThrowable(()-> toTest.toggleGroup(0));
        //then
        assertThat(exception).isInstanceOfAny(IllegalArgumentException.class)
                .hasMessageContaining("Task with given id not found");
    }

    @Test
    @DisplayName("should switch state of group 'done' field")
    void toggleGroup_noUndoneTasksExists_toggleDoneField() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //and
        TaskGroup group = new TaskGroup();
        var beforeToggle = group.isDone();
        //and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //when
        toTest.toggleGroup(0);
        //then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }

    private TaskRepository taskRepositoryReturning(boolean t) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(t);
        return mockTaskRepository;
    }
}