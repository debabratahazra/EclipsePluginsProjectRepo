package com.temenos.t24.tools.eclipse.basic.document.parser;

import java.util.ArrayList;
import java.util.List;

public class BatchInfo {

    private String description;
    private String name;
    private List<JobInfo> jobs;

    private BatchInfo(String name, String description) {
        jobs = new ArrayList<JobInfo>();
        this.name = name;
        this.description = description;
    }

    public void addJob(JobInfo job) {
        jobs.add(job);
    }

    public String getDescription() {
        return description;
    }

    public String getBatchName() {
        return name;
    }

    public List<JobInfo> getJobs() {
        return jobs;
    }

    public static BatchInfo newBatch(String name, String description) {
        return new BatchInfo(name, description);
    }
}
