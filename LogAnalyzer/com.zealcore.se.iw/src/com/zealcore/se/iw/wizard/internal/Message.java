package com.zealcore.se.iw.wizard.internal;

public class Message extends AbstractTextNode {

    Message() {}

    @Override
    public void accept(final AbstractTextTreeVisitor visitor) {
        visitor.visitMessage(this);
        for (final AbstractTextNode node : getChildren()) {
            node.accept(visitor);
        }
    }
}
