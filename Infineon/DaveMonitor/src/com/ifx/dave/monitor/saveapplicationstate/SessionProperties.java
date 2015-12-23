package com.ifx.dave.monitor.saveapplicationstate;

import java.util.List;

public class SessionProperties {

    ScopeProperties scopeProperties;
    List<TableViewProperties> tableviewProperties;
    String elfFilePath;
    String deviceName;
    String testString = "XSPYString";

    public String getTestString() {
        return testString;
    }
}
