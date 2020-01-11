package com.fromLab;

import org.apache.commons.codec.binary.Base64;

public class OpenprojectURL {
    public static final String PROJECTS_URL ="/api/v3/projects";
    public static final String PROJECT_URL="/api/v3/projects/{0}";
    public static final String WORKPAGES_URL="/api/v3/work_packages/";
    private static final String PATH_PARAM_ARG_PREFIX = "\\{";
    private static final String PATH_PARAM_ARG_SUFFIX = "\\}";
    private static final String KEY_QUERY_PARAM = "?key=";
    private static final String TOKEN_QUERY_PARAM = "&token=";
    private static final String FILTER_QUERY_PARAM = "&filter=";
    private final String openProjectURL;
    private final String url;
    private final String[] pathParams;
    private final String apiKey;
    private String token = null;
    private String[] filters = null;
    private OpenprojectURL(String openProjectURL, String apiKey, String url, String... pathParams){
        this.openProjectURL="https://pluginide.openproject.com";
        this.apiKey = apiKey;
        this.url = url;
        this.pathParams = pathParams;
    }
    public String encoding(String apikey){
        Base64 b = new Base64();
        String key="apikey:"+apikey;
        String encoding = b.encodeAsString(key.getBytes());
        return encoding;
    }
    public static OpenprojectURL create(String openProjectURL, String apiKey, String url,
                                        String... pathParams) {
        return new OpenprojectURL(openProjectURL,apiKey, url, pathParams);
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


}
