package com.fromLab.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public enum Status {
    NEW(1,"new"),
    InSpecification(2,"In specification"),
    Specified(3,"Specified"),
    Confirmed(4,"Confirmed"),
    ToBeScheduled(5,"To be scheduled"),
    Scheduled(6,"Scheduled"),
    InProgress(7,"In progress"),
    InDevelopment(8,"In development"),
    Developed(9,"Developed"),
    InTesting(10,"In Testing"),
    Tested(11,"Tested"),
    TestedFailed(12,"Test failed"),
    Closed(13,"Closed"),
    OnHold(14,"On hold"),
    Rejected(15,"Reject");
    private int id;
    private String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String toJson(int lock_version){
        JsonObject body=new JsonObject();
        body.addProperty("lockVersion",lock_version);
        JsonObject status1=new JsonObject();
        status1.addProperty("href","/api/v3/statuses/"+id);
       // status1.addProperty("title",name);

        JsonObject links=new JsonObject();
        links.add("status",status1);
        body.add("_links",links);
        return body.toString();
    }
}
