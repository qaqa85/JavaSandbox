package com.webExample.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {
    public Template template;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public static class Template {
        private boolean allowMultipleTasks;

        public boolean isAllowMultipleTasks() {
            return allowMultipleTasks;
        }

        public void setAllowMultipleTasks(boolean allowMultipleTasks) {
            this.allowMultipleTasks = allowMultipleTasks;
        }
    }
}
