/*
 * 
 */
package com.zealcore.se.core.model;

import com.zealcore.se.core.annotation.ZCProperty;

/**
 * The Interface ISequenceMessage. The concrete implementation must also extend
 * {@link ILogEvent} or any its sublasses.
 */
public interface ISequenceMessage extends ITimed, IObject {

    @ZCProperty
    ISequenceMember getSender();

    @ZCProperty
    ISequenceMember getRecipent();

    void setRecipent(ISequenceMember recipent);

    void setSender(ISequenceMember sender);

    String getMessage();

    long getTs();

    void setTs(long ts);

    long getDeliveryTime();

    long getDrawingTs();

    void setDrawingTs(long ts);
}
