package com.zealcore.se.ui.mock;

import java.util.ArrayList;
import java.util.Collection;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractArtifact;

public class ArtifactRoot extends AbstractArtifact {

    private final Collection<Signal> signals = new ArrayList<Signal>();

    @ZCProperty(name = "Signals")
    public Collection<Signal> getSignals() {
        return this.signals;
    }

}
