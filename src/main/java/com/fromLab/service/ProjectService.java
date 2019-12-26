package com.fromLab.service;

import com.fromLab.entity.Project;

import java.util.List;

public interface ProjectService {
    Project getProjectByID(String id);

    Project UpdateProjectByID(Project project,String id);

    Project DeleteProjectByID(String id);

    List<Project> getProjects(String sortby,String... filiters);

}
