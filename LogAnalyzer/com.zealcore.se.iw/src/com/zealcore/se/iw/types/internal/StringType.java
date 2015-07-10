package com.zealcore.se.iw.types.internal;

public class StringType implements IFieldType {

    public static final String ID = "TYPE_STRING";

    private static final String STRING = "String";

    private static StringType instance;

    public String getLabel() {
        return StringType.STRING;
    }

    public String valueOf(final String text) {
        return text;
    }

    public static IFieldType getInstance() {
        if (StringType.instance == null) {
            StringType.instance = new StringType();
        }
        return StringType.instance;
    }

    public String getId() {
        return StringType.ID;
    }

    public boolean canMatch(final String proposal) {
        return true;
    }

}
