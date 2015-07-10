package com.zealcore.se.iw.wizard.internal;

import java.util.ArrayList;
import java.util.Collection;

abstract class AbstractTextNode {

    private String data;

    private final Collection<AbstractTextNode> children = new ArrayList<AbstractTextNode>();

    public int start() {
        int start = Integer.MAX_VALUE;
        for (final AbstractTextNode child : getChildren()) {
            start = Math.min(child.start(), start);
            if (start <= 0) {
                return 0;
            }
        }
        return start;
    }

    public int length() {
        int length = 0;
        for (final AbstractTextNode node : getChildren()) {
            length += node.length();
        }
        return length;
    }

    @Override
    public String toString() {
        if (this.data != null) {
            return this.data;
        }
        final StringBuilder toString = new StringBuilder();
        for (final AbstractTextNode node : getChildren()) {
            toString.append(node.toString());
        }
        this.data = toString.toString();
        return this.data;
    }

    void add(final AbstractTextNode node) {
        this.children.add(node);
    }

    public Collection<? extends AbstractTextNode> getChildren() {
        return this.children;
    }

    public abstract void accept(AbstractTextTreeVisitor visitor);
}
