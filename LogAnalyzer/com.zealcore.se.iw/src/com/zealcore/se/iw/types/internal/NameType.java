package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;

public class NameType implements IModifyingFieldType {

    private static final String TYPE_EVENT_NAME = "TYPE_EVENT_NAME";

    public String getLabel() {
        return "Event Type Name";
    }

    public String valueOf(final String text) {
        return text;
    }

    public void modify(final GenericLogEvent event, final String value) {
        event.setTypeName(value);
    }

    public String getId() {
        return NameType.TYPE_EVENT_NAME;
    }

    public boolean canMatch(final String proposal) {
        return true;
    }
}
