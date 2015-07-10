package com.zealcore.se.iw.wizard.internal;

public abstract class AbstractTextTreeVisitor {

    public void visitTree(final MessageTree tree) {}

    public void visitHeader(final Header header) {}

    public void visitMessage(final Message message) {}

    public void visitMessageEnd() {}

    public void visitField(final Field field) {}

}
