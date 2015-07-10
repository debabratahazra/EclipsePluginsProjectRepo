package com.zealcore.se.ui.mock;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractArtifact;

public class Signal extends AbstractArtifact {

    private StackTrace stackTrace;

    @ZCProperty(name = "StackTrace")
    public StackTrace getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(final StackTrace stackTrace) {
        this.stackTrace = stackTrace;
    }

}
