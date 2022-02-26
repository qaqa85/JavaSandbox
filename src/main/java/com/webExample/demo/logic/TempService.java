package com.webExample.demo.logic;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class TempService {
    private TaskGroupRepository r;

    @Autowired
    List<String> temp(TaskGroupRepository repository) {
        //N + 1 because of lazy loading
       return repository.findAll().stream()
               .flatMap(taskGroup -> taskGroup.getTasks().stream())
               .map(Task::getDescription).collect(Collectors.toList());
    }
}
