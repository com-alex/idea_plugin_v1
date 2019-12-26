package com.fromLab.service.impl;

import com.fromLab.OpenprojectURL;
import com.fromLab.entity.Project;
import com.fromLab.service.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    private String apiKey = null;
    private String token = null;
    public ProjectServiceImpl(String apiKey) {
        this.apiKey=apiKey;
    }

    @Override
    public Project getProjectByID(String id) {
        return null;
    }

    @Override
    public Project UpdateProjectByID(Project project, String id) {
        return null;
    }

    @Override
    public Project DeleteProjectByID(String id) {
        return null;
    }

    @Override
    public List<Project> getProjects(String sortby, String... filiters) {
        final String url=
                OpenprojectURL.create("https://plugin.openproject.com",apiKey,OpenprojectURL.PROJECTS_URL)
                .filter(filiters)
                .build();
        System.out.println(url);
        return null;
    }

    public static void main(String[] args) {
        ProjectServiceImpl imp=new ProjectServiceImpl("53e1d54ae78a000b5eecf4c95235b03d6099e394c3e03b2c28ba48eb7ef2f1b6");
        imp.getProjects("");
    }
}
