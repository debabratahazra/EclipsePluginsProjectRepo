package com.zealcore.se.ui.mock;

import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;

public class MockActor extends AbstractArtifact implements ISequenceMember {

    private ISequence parent;

    public ISequence getParent() {
        return this.parent;
    }

    public void setParent(final ISequence parent) {
        this.parent = parent;

    }

}
