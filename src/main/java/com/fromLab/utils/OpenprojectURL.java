package com.fromLab.utils;

import okhttp3.*;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class OpenprojectURL {

    public static final String API_URL = "/api/v3";
    public static final String WORK_PACKAGES_URL = "/api/v3/work_packages/";
    private String openProjectURL;
    private String apiKey;


    public OpenprojectURL(String openProjectURL, String apiKey){
        this.openProjectURL = openProjectURL;
        this.apiKey = apiKey;
    }

    public String getOpenProjectURL() {
        return openProjectURL;
    }

    public void setOpenProjectURL(String openProjectURL) {
        this.openProjectURL = openProjectURL;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }



    // get json from PMS server using Okhttp
    public String getJson() {
        Base64 b = new Base64();
        String key = "apikey:" + this.apiKey;
        String encoding = b.encodeAsString(key.getBytes());
        String tail = "Basic " + encoding;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        try {
            Request request = new Request.Builder()
                    .url(this.openProjectURL)
                    .method("GET", null)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Authorization", tail)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {

        }
        return "Error";
    }


    private static boolean isArrayEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }


    //update the record
    public String patch(String json) {
        Base64 b = new Base64();
        String key = "apikey:" + this.apiKey;
        String encoding = b.encodeAsString(key.getBytes());
        String tail = "Basic " + encoding;
//        System.out.println(url);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(this.openProjectURL)
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


    @Override
    public String toString() {
        return "OpenprojectURL{" +
                "openProjectURL='" + openProjectURL + '\'' +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }
}
