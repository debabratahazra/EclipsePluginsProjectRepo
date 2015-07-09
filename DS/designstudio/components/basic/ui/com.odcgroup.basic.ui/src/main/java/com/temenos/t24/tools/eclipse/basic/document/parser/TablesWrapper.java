package com.temenos.t24.tools.eclipse.basic.document.parser;

import java.util.ArrayList;
import java.util.List;

public class TablesWrapper {

    private String overview;
    private List<TableInfo> tables;
    public static TablesWrapper EMPTY = new TablesWrapper("NO TABLES AVAILABLE");

    public TablesWrapper(String overview) {
        this.overview = overview;
        tables = new ArrayList<TableInfo>();
    }

    public String getOverview() {
        return overview;
    }

    public List<TableInfo> getTables() {
        return tables;
    }

    public void addTable(TableInfo table) {
        tables.add(table);
    }
}
