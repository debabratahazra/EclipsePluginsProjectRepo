package com.zealcore.se.iw.wizard.internal;

import java.util.Collection;
import java.util.Collections;

import com.zealcore.se.iw.FieldDescriptor;

public class Field extends AbstractTextNode {

    private final String data;

    private final int position;

    private final FieldDescriptor creator;

    private boolean isMatchedInFieldProposal;

    Field(final int position, final String text, final FieldDescriptor desc,
            final boolean isMatchedInFieldProposal) {
        this.position = position;
        this.data = text;
        this.creator = desc;
        this.isMatchedInFieldProposal = isMatchedInFieldProposal;
    }

    @Override
    public int length() {
        return this.data.length();
    }

    @Override
    public int start() {
        return this.position;
    }

    @Override
    public String toString() {
        return this.data;
    }

    @Override
    public Collection<? extends AbstractTextNode> getChildren() {
        return Collections.emptyList();
    }

    public FieldDescriptor getDescriptor() {
        return this.creator;
    }

    @Override
    public void accept(final AbstractTextTreeVisitor visitor) {
        visitor.visitField(this);
    }

    public boolean isMatchedInFieldProposal() {
        return this.isMatchedInFieldProposal;
    }

    public void setMatchedInFieldProposal(final boolean isMatchedInFieldProposal) {
        this.isMatchedInFieldProposal = isMatchedInFieldProposal;
    }
}
