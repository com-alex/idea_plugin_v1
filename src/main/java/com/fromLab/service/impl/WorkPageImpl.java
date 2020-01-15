package com.fromLab.service.impl;

import com.fromLab.OpenprojectURL;
import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.Status;
import com.fromLab.entity.WorkPage;
import com.fromLab.service.WorkPageService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
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

    public static void main(String[] args) {
        WorkPageImpl wi=new WorkPageImpl();
        Filter filter=new Filter("project_id","1");
        Filter filter1=new Filter("type_id","1");
        ArrayList<Filter> filters=new ArrayList<>();
        filters.add(filter);
        filters.add(filter1);
        SortBy sortBy=new SortBy("status","asc");
        wi.UpdatePercentageDone("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",4,2,100);
        wi.getTaskById("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",1);
       // wi.getTasks("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",
            //    filters,sortBy,"status");
    //    wi.updateStatus("https://pluginide.openproject.com","d283df40b49674c4805a088f6b6f0b109276627df1fc24057e985ee3c0f6bbc2",1,Status.Scheduled,4);
    }
}
