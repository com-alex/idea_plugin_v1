package com.fromLab.service.impl;

import com.fromLab.OpenprojectURL;
import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.WorkPage;
import com.fromLab.service.WorkPageService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

public class WorkPageImpl implements WorkPageService {
    @Override
    public List<WorkPage> getWorkPages(Filter filter, SortBy sortBy, String groupBy) {
        String filterJson=filter.toJson();
        String url= "https://pluginide.openproject.com"+OpenprojectURL.WORKPAGES_URL+"?filters="+filterJson+"&sortBy="+sortBy.toJson()
                +"&groupBy="+groupBy;
        System.out.println(url);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", "Basic YXBpa2V5OmQyODNkZjQwYjQ5Njc0YzQ4MDVhMDg4ZjZiNmYwYjEwOTI3NjYyN2RmMWZjMjQwNTdlOTg1ZWUzYzBmNmJiYzI=")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        WorkPageImpl wi=new WorkPageImpl();
        Filter filter=new Filter("project_id","1");
        SortBy sortBy=new SortBy("status","asc");
        wi.getWorkPages(filter,sortBy,"status");
    }
}
