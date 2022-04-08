package com.webExample.demo.model.projection;

import com.webExample.demo.model.Project;
import com.webExample.demo.model.ProjectStep;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter @Setter
public class ProjectWriteModel {
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    @Valid
    private List<ProjectStep> steps = new ArrayList<>();

    int id;

    public  ProjectWriteModel() {
        steps.add(new ProjectStep());
    }

    public Project toProject() {
        var result = new Project();
        result.setId(id);
        result.setDescription(description);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}
