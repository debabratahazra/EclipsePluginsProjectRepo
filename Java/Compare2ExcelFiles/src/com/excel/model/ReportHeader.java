package com.excel.model;

import java.util.ArrayList;
import java.util.List;

public class ReportHeader {

    private static final ReportHeader header = new ReportHeader();

    private ReportHeader() {
    }

    public static ReportHeader getInstance() {
        return header;
    }

    private List<String> reportHeaders = new ArrayList<String>();;

    public void addReportHeaders(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            reportHeaders.add(strings[i]);
        }
    }

    public String[] getReportHeader() {
        String[] headerNames = new String[reportHeaders.size()];
        headerNames = reportHeaders.toArray(headerNames);
        return headerNames;
    }

    public int getSize() {
        return reportHeaders.size();
    }
}
