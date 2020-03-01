package com.fromLab.utils;

import okhttp3.*;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class OpenprojectURL {
    public static final String PROJECTS_URL = "/api/v3/projects";
    public static final String PROJECT_URL = "/api/v3/projects/";
    public static final String API_URL = "/api/v3";
    public static final String WORKPAGES_URL = "/api/v3/work_packages/";
    private String openProjectURL;
    private String url;
    private String[] pathParams;
    private String apiKey;
    private String token = null;
    private String[] filters = null;

    public OpenprojectURL(String openProjectURL, String apiKey, String url) {
        this.openProjectURL = "http://projects.plugininide.com/openproject";
        this.apiKey = apiKey;
        this.url = url;
    }

    public String encoding(String apikey) {
        Base64 b = new Base64();
        String key = "apikey:" + apikey;
        String encoding = b.encodeAsString(key.getBytes());
        return encoding;
    }



    public String getJson(String url) {
        Base64 b = new Base64();
        String key = "apikey:" + apiKey;
        String encoding = b.encodeAsString(key.getBytes());
        String tail = "Basic " + encoding;
        System.out.println(url);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", tail)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {

        }
        return "Error";
    }

    public static OpenprojectURL create(String openProjectURL, String apiKey, String url
    ) {
        return new OpenprojectURL(openProjectURL, apiKey, url);
    }

    public OpenprojectURL filter(String... filters) {
        this.filters = isArrayEmpty(filters) ? null : filters;
        return this;
    }

    private static boolean isArrayEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }


    public String patch(String url, String json) {
        Base64 b = new Base64();
        String key = "apikey:" + apiKey;
        String encoding = b.encodeAsString(key.getBytes());
        String tail = "Basic " + encoding;
//        System.out.println(url);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .method("PATCH", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", tail)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }



    public static void main(String[] args) {
        OpenprojectURL openprojectURL = new OpenprojectURL("https://pluginide.openproject.com",
                "d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2", OpenprojectURL.WORKPAGES_URL);
    }

}
