package com.fromLab.service.impl;

import com.fromLab.OpenprojectURL;
import com.fromLab.entity.Project;
import com.fromLab.service.ProjectService;
import com.fromLab.utils.HttpBasicAuth;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;

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
        JSONObject json = hb.getJson(url, apiKey);
        System.out.println(json);
        return null;
    }

    public static void main(String[] args) {
        ProjectServiceImpl imp=new ProjectServiceImpl("e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123");
        imp.getProjects("");
    }
}
