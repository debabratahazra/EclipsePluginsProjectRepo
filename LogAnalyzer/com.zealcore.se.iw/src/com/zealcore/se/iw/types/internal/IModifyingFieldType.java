package com.zealcore.se.iw.types.internal;

import com.zealcore.se.core.model.generic.GenericLogEvent;

public interface IModifyingFieldType extends IFieldType {
    void modify(GenericLogEvent event, String value);
}
