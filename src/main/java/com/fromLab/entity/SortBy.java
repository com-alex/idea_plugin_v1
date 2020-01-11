package com.fromLab.entity;

import com.google.gson.JsonArray;

public class SortBy {
    private String name;
    private String value;

    public SortBy(String name, String value) {
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
        JsonArray jsonArray1=new JsonArray();
        JsonArray jsonArray=new JsonArray();
        jsonArray.add(name);
        jsonArray.add(value);
        jsonArray1.add(jsonArray);
        return jsonArray1.toString();
    }

    public static void main(String[] args) {
        SortBy sortBy=new SortBy("id","asc");
        System.out.println(sortBy.toJson());
    }
}
