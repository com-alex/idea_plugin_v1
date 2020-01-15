package com.fromLab;

import okhttp3.*;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

public class OpenprojectURL {
    public static final String PROJECTS_URL ="/api/v3/projects";
    public static final String PROJECT_URL="/api/v3/projects/";
    public static final String WORKPAGES_URL="/api/v3/work_packages/";
    private static final String PATH_PARAM_ARG_PREFIX = "\\{";
    private static final String PATH_PARAM_ARG_SUFFIX = "\\}";
    private static final String KEY_QUERY_PARAM = "?key=";
    private static final String TOKEN_QUERY_PARAM = "&token=";
    private static final String FILTER_QUERY_PARAM = "&filter=";
    private  String openProjectURL;
    private  String url;
    private  String[] pathParams;
    private  String apiKey;
    private String token = null;
    private String[] filters = null;
     public OpenprojectURL(String openProjectURL, String apiKey, String url){
        this.openProjectURL="https://pluginide.openproject.com";
        this.apiKey = apiKey;
        this.url = url;
    }
    public String encoding(String apikey){
        Base64 b = new Base64();
        String key="apikey:"+apikey;
        String encoding = b.encodeAsString(key.getBytes());
        return encoding;
    }
    public String getJson (String url){
        Base64 b = new Base64();
        String key="apikey:"+apiKey;
        String encoding = b.encodeAsString(key.getBytes());
        String tail="Basic "+encoding;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
    public static OpenprojectURL create(String openProjectURL, String apiKey, String url
                                        ) {
        return new OpenprojectURL(openProjectURL,apiKey, url);
    }
    public OpenprojectURL filter(String... filters) {
        this.filters = isArrayEmpty(filters) ? null : filters;
        return this;
    }
    private static boolean isArrayEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }
    public String build() {
        if (apiKey == null || url == null) {
            throw new NullPointerException(
                    "Cannot build trello URL: API key and URL must be set");
        }

        return new StringBuilder()
                .append(openProjectURL)
                .append(createUrlWithPathParams())
              //  .append(createAuthQueryString())
                .append(createFilterQuery())
                .toString();
    }
    public String patch (String url,String json){
        Base64 b = new Base64();
        String key="apikey:"+apiKey;
        String encoding = b.encodeAsString(key.getBytes());
        String tail="Basic "+encoding;
        System.out.println(url);
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

    private String createFilterQuery() {
        String filterStr = "";
        if (this.filters != null) {
            StringBuilder sb = new StringBuilder(FILTER_QUERY_PARAM);
            for (int i = 0; i < filters.length; i++) {
                sb.append(i > 0 ? "," : "").append(filters[i]);
            }
            filterStr = sb.toString();
        }
        return filterStr;
    }

    private String createAuthQueryString() {
        StringBuilder sb = new StringBuilder(KEY_QUERY_PARAM).append(apiKey);

        if (this.token != null) {
            sb.append(TOKEN_QUERY_PARAM).append(this.token);
        }
        return sb.toString();
    }

    private String createUrlWithPathParams() {
        if (pathParams == null || pathParams.length == 0) return url;
        String compiledUrl = null;
        for (int i = 0; i < pathParams.length; i++) {
            compiledUrl = url.replaceAll(PATH_PARAM_ARG_PREFIX + i
                    + PATH_PARAM_ARG_SUFFIX, pathParams[i]);
        }
        // boardUrl += authQueryString;
        return compiledUrl;
    }

    public static void main(String[] args) {
        OpenprojectURL openprojectURL= new OpenprojectURL("https://pluginide.openproject.com",
                "d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",OpenprojectURL.WORKPAGES_URL);
    }

}
