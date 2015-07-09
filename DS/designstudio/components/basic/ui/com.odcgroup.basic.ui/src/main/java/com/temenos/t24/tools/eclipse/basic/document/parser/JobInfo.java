package com.temenos.t24.tools.eclipse.basic.document.parser;

public class JobInfo {

    private String description;
    private String name;

    private JobInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getJobName() {
        return name;
    }

    public static JobInfo newJob(String name, String description) {
        return new JobInfo(name, description);
    }
}
