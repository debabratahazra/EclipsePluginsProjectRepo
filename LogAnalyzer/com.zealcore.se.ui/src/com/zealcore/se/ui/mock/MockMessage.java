package com.zealcore.se.ui.mock;

import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;

public class MockMessage extends AbstractLogEvent implements ISequenceMessage {

    private ISequenceMember recipent;

    private ISequenceMember sender;

    private long drawingTs;

    public ISequenceMember getRecipent() {
        return this.recipent;
    }

    public ISequenceMember getSender() {
        return this.sender;
    }

    public void setRecipent(final ISequenceMember recipent) {
        this.recipent = recipent;
    }

    public void setSender(final ISequenceMember sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return "ping()";
    }

    public long getDeliveryTime() {
        return 0;
    }

    public long getDrawingTs() {
        return drawingTs;
    }

    public void setDrawingTs(long ts) {
        this.drawingTs = ts;
    }

}
