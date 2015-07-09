package com.temenos.t24.tools.eclipse.basic.document.parser;

public class TableInfo {

    private String tableName;
    private String description;
    private String type;
    private String classification;

    private TableInfo() {
    }

    public String getTableName() {
        return tableName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getClassification() {
        return classification;
    }

    public static TableInfo newTable(String tableName, String description, String type, String classification) {
        TableInfo newTable = new TableInfo();
        newTable.tableName = tableName;
        newTable.description = description;
        newTable.type = type;
        newTable.classification = classification;
        return newTable;
    }
}
