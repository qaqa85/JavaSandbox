package com.webExample.demo;

import com.webExample.demo.model.Task;
import com.webExample.demo.model.TaskGroup;
import com.webExample.demo.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    public static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository groupRepository;

    Warmup(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");
        final String description = "ApplicationContextEvent";
        if(!groupRepository.existsByDescription(description)) {
            logger.info("No required group found! Adding it");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(
                    new Task(null ,"ContextClosedEvent", group),
                    new Task(null ,"ContextRefreshedEvent", group),
                    new Task(null ,"ContextStoppedEvent", group),
                    new Task(null ,"ContextStartedEvent", group)
            ));
            groupRepository.save(group);
        }
    }
}
