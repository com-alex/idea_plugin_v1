package com.fromLab.service;

import com.fromLab.entity.Filter;
import com.fromLab.entity.SortBy;
import com.fromLab.entity.WorkPage;


import java.util.List;

public interface WorkPageService {
    List<WorkPage> getWorkPages(Filter filter, SortBy sortBy, String groupBy);

}
