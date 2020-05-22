package com.fromLab.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public enum Status {
    NEW(1, "New"),
    InProgress(7, "In progress"),
    Closed(13, "Closed"),
    OnHold(14, "On hold"),
    Rejected(15, "Reject");
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

    public String toJson(int lock_version) {
        JsonObject body = new JsonObject();
        body.addProperty("lockVersion", lock_version);
        JsonObject status1 = new JsonObject();
        status1.addProperty("href", "/api/v3/statuses/" + id);
        // status1.addProperty("title",name);

        JsonObject links = new JsonObject();
        links.add("status", status1);
        body.add("_links", links);
        return body.toString();
    }

    public JsonObject statusJsonObject() {
        JsonObject status1 = new JsonObject();
        status1.addProperty("href", "/api/v3/statuses/" + id);
        return status1;
    }
}
