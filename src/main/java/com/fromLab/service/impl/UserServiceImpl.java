package com.fromLab.service.impl;

import com.fromLab.service.UserService;
import com.fromLab.utils.OpenprojectURL;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * @author wsh
 * @date 2020-03-01
 */
public class UserServiceImpl implements UserService {

    private static final String ERROR_STRING = "Error";

    @Override
    public Boolean authorize(String openProjectURl, String apiKey) {
        OpenprojectURL openprojectURL = new OpenprojectURL(openProjectURl + OpenprojectURL.API_URL, apiKey);
        String response = openprojectURL.getJson();
        if(StringUtils.equals(response, ERROR_STRING)){
            //Authentication failed, url error
            return false;
        }
        JSONObject responseObject = null;
        try{
           responseObject = JSONObject.fromObject(response);
        }catch (Exception e){
            return false;
        }
        String type = (String)responseObject.get("_type");
        if(StringUtils.equals(type, ERROR_STRING)){
            //Authentication failed, API key error
            return false;
        }
        return true;
    }
}
