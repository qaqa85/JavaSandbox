package com.webExample.demo.controller;

import com.webExample.demo.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    final private DataSourceProperties dataSource;
    final private TaskConfigurationProperties myProp;

    public InfoController(DataSourceProperties dataSource, TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url() {
        return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    boolean myProp() {
        return myProp.template.isAllowMultipleTasks();
    }
}
