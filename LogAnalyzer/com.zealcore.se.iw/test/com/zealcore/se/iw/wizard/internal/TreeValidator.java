/**
 * 
 */
package com.zealcore.se.iw.wizard.internal;

import junit.framework.Assert;

final class TreeValidator extends AbstractTextTreeVisitor {

    private int messageCount;

    private int headerCount;

    private int fieldCount;

    private final int numFields;

    private final int numMessages;

    private final int numHeaders;

    public TreeValidator(final int numHeaders, final int numMessages,
            final int numFields) {
        this.numHeaders = numHeaders;
        this.numMessages = numMessages;
        this.numFields = numFields;

    }

    @Override
    public void visitHeader(final Header header) {
        this.headerCount++;
    }

    @Override
    public void visitMessage(final Message message) {
        this.messageCount++;
    }

    @Override
    public void visitField(final Field field) {

        this.fieldCount++;
    }

    void verify() {
        Assert.assertEquals("Unexpected number of messages", this.numMessages,
                this.messageCount);
        Assert.assertEquals("Unexpected number of headers", this.numHeaders,
                this.headerCount);
        Assert.assertEquals("Unexpected number of fields", this.messageCount
                * this.numFields, this.fieldCount);
    }

}
