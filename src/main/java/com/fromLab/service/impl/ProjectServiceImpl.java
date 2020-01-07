package com.fromLab.service.impl;

import com.fromLab.OpenprojectURL;
import com.fromLab.entity.Project;
import com.fromLab.service.ProjectService;
import com.fromLab.utils.HttpBasicAuth;
import com.google.gson.JsonObject;

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
                OpenprojectURL.create("https://pluginide.openproject.com",apiKey,OpenprojectURL.PROJECTS_URL)
                .filter(filiters)
                .build();
        HttpBasicAuth hb=new HttpBasicAuth();
       JsonObject jsonObject= hb.getJson(url,apiKey);
        System.out.println(jsonObject);
        return null;
    }

    public static void main(String[] args) {
        ProjectServiceImpl imp=new ProjectServiceImpl("d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2");
        imp.getProjects("");
    }
}
