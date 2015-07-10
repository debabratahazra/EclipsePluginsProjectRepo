package com.zealcore.se.iw.wizard.internal;

import java.util.Collection;
import java.util.Collections;

public class Header extends AbstractTextNode {

    private final String text;

    private final int pos;

    Header(final int pos, final String text) {
        this.pos = pos;
        this.text = text;
    }

    @Override
    public Collection<? extends AbstractTextNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public int start() {
        return this.pos;
    }

    @Override
    public int length() {
        return this.text.length();
    }

    @Override
    public void accept(final AbstractTextTreeVisitor visitor) {
        visitor.visitHeader(this);
    }

    @Override
    public String toString() {
        return this.text;
    }
}
