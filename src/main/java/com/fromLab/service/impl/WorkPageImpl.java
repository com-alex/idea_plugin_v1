package com.fromLab.service.impl;

import com.fromLab.utils.OpenprojectURL;
import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.WorkPage;
import com.fromLab.service.WorkPageService;
import com.google.gson.JsonArray;

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

}
