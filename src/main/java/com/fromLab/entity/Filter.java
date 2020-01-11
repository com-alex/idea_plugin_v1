package com.fromLab.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Filter {
    private String name;
    private String value;

    public Filter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toJson(){
        JsonArray jsonArray=new JsonArray();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("operator","=");
        jsonObject.addProperty("values",value);
        JsonObject jsonObject1=new JsonObject();
        jsonObject1.add(name,jsonObject);
        jsonArray.add(jsonObject1);
        return jsonArray.toString();
    }
}
