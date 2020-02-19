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
    WorkPage updateStartDate(String openprojectURL, String apiKey, int id, int lock_version, String start_date);
    WorkPage updateEndDate(String openprojectURL, String apiKey, int id, int lock_version, String end_date,String customField );
    WorkPage updateSpentTime(String openprojectURL, String apiKey, int id, int lock_version, int time,String customField );
   // Task updateLocalProperties(String openprojectURL, String apiKey, HashMap<Object,Object> properties,int lock_version);
}
