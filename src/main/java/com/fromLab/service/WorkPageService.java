package com.fromLab.service;

import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.Status;
import com.fromLab.entity.WorkPage;


import java.util.Date;
import java.util.List;

public interface WorkPageService {
    List<WorkPage> getTasks(String openprojectURL, String apiKey, List<Filter> filters, SortBy sortBy, String groupBy);
    WorkPage updateStatus(String openprojectURL, String apikey, int id, Status status, int lock_version);
    WorkPage getTaskById(String openprojectURL, String apiKey, int id);
    WorkPage UpdatePercentageDone(String openprojectURL, String apiKey, int id, int lock_version, int percentage);
    void updateStartDate(String openprojectURL, String apiKey, int id, int lock_version, String start_date);
    void updateEndDate(String openprojectURL, String apiKey, int id, int lock_version, String end_date,String customField );
    void updateSpentTime(String openprojectURL, String apiKey, int id, int lock_version, int time,String customField );
    void updateStatusAndProgress(String openprojectURL, String apiKey, int id, int lock_version,Status status,int percentage);
   // Task updateLocalProperties(String openprojectURL, String apiKey, HashMap<Object,Object> properties,int lock_version);
}
