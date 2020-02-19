package com.fromLab.service.impl;

import com.fromLab.OpenprojectURL;
import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.Status;
import com.fromLab.entity.WorkPage;
import com.fromLab.service.WorkPageService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkPageImpl implements WorkPageService {
    @Override
    public List<WorkPage> getTasks(String openprojectURL, String apikey, List<Filter> filters, SortBy sortBy, String groupBy) {
        JsonArray jsonArray=new JsonArray();
        for(int i=0;i<filters.size();i++){
            jsonArray.add(filters.get(i).toJsonObj());
        }
        String filterJson=jsonArray.toString();
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apikey,OpenprojectURL.WORKPAGES_URL);
        String json= o.getJson(openprojectURL+OpenprojectURL.WORKPAGES_URL+
                "?filters="+filterJson
        +"&sortBy="+sortBy.toJson()
        +"&groupBy="+groupBy);
        System.out.println(json);
        return null;
    }

    @Override
    public WorkPage updateStatus(String openprojectURL, String apiKey, int id, Status status, int lock_version) {
        String json=status.toJson(lock_version);
        System.out.println(json);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,json);
        System.out.println(result);

        return null;
    }

    @Override
    public WorkPage getTaskById(String openprojectURL, String apiKey, int id) {
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String json= o.getJson(openprojectURL+OpenprojectURL.WORKPAGES_URL+id);
        System.out.println(json);
        return null;
    }

    @Override
    public WorkPage UpdatePercentageDone(String openprojectURL, String apiKey, int id, int lock_version, int percentage) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty("percentageDone",percentage);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
        return null;
    }

    @Override
    public void updateStartDate(String openprojectURL, String apiKey, int id, int lock_version, String start_date) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty("startDate",start_date);
        System.out.println(jsonObject);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
        System.out.println(result);
    }

    @Override
    public void updateEndDate(String openprojectURL, String apiKey, int id, int lock_version, String end_date, String customField) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty(customField,end_date);
        System.out.println(jsonObject);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
        System.out.println(result);
    }

    @Override
    public void updateSpentTime(String openprojectURL, String apiKey, int id, int lock_version, int time, String customField) {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("lockVersion",lock_version);
        jsonObject.addProperty(customField,time);
        System.out.println(jsonObject);
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,jsonObject.toString());
        System.out.println(result);
    }

    @Override
    public void updateStatusAndProgress(String openprojectURL, String apiKey, int id, int lock_version, Status status, int percentage) {
        JsonObject body=new JsonObject();
        body.addProperty("lockVersion",lock_version);
        body.addProperty("percentageDone",percentage);
        JsonObject status1=new JsonObject();
        status1.addProperty("href","/api/v3/statuses/"+status.getId());
        JsonObject links=new JsonObject();
        links.add("status",status1);
        body.add("_links",links);
        System.out.println(body.toString());
        OpenprojectURL o=new OpenprojectURL(openprojectURL,apiKey,OpenprojectURL.WORKPAGES_URL);
        String result=o.patch(openprojectURL+OpenprojectURL.WORKPAGES_URL+id,body.toString());
        System.out.println(result);

    }

    public static void main(String[] args) {
        WorkPageImpl wi=new WorkPageImpl();
        Filter filter=new Filter("project_id","1");
        Filter filter1=new Filter("type_id","1");
        ArrayList<Filter> filters=new ArrayList<>();
        filters.add(filter);
        filters.add(filter1);
        SortBy sortBy=new SortBy("status","asc");
        String url="http://192.168.199.129/openproject";
        String apikey="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";
        wi.updateStatusAndProgress(url,apikey,8,11,Status.InProgress,100);
       // wi.updateStatus(url,apikey,8,Status.InProgress,6);
      //  wi.updateSpentTime(url,apikey,8,5,10,"customField4");
     //   wi.UpdatePercentageDone("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",4,2,100);
      //  wi.getTaskById("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",1);
       // wi.getTasks("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",
            //    filters,sortBy,"status");
    //    wi.updateStatus("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",1,Status.Scheduled,4);
    }
}
