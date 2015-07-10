package com.zealcore.se.iw.wizard.internal;

public class MessageTree extends AbstractTextNode {

    MessageTree() {

    }

    @Override
    public void accept(final AbstractTextTreeVisitor visitor) {
        visitor.visitTree(this);
        for (final AbstractTextNode node : getChildren()) {
            node.accept(visitor);
        }
    }
}
