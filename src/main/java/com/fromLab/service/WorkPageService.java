package com.fromLab.service;

import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.Status;
import com.fromLab.entity.WorkPage;


import java.util.Date;
import java.util.List;

public interface WorkPageService {
    List<WorkPage> getTasks(String openprojectURL, String apiKey, List<Filter> filters, SortBy sortBy, String groupBy);
}
