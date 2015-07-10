package com.zealcore.se.iw.types.internal;

public interface IFieldType {
    Object valueOf(String text);

    String getLabel();

    String getId();

    // Returns true if the argument/proposal is of the format that it is
    // possible to parse for the implementing type
    boolean canMatch(String proposal);

}
